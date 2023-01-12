package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import nextstep.RoomEscapeWebApplication;
import nextstep.domain.Theme;
import nextstep.dto.ReservationRequestDto;
import nextstep.repository.RepositoryTestExecutionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestExecutionListeners;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RoomEscapeWebApplication.class)
@TestExecutionListeners(listeners = RepositoryTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class ReservationControllerTest {

    Theme DEFAULT_THEME = new Theme("검은방", "밀실 탈출", 30_000);
    ReservationRequestDto testRequestDto;
    DateTimeFormatter dateFormatter;
    DateTimeFormatter timeFormatter;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        testRequestDto = generateReservationRequestDto("2023-01-01", "13:00", "john");
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    private ReservationRequestDto generateReservationRequestDto(String date, String time, String name) {
        return new ReservationRequestDto(
                LocalDate.parse(date),
                LocalTime.parse(time),
                name
        );
    }

    @DisplayName("예약 생성 요청이 성공하면 201 코드와 Location 헤더 반환")
    @Test
    void reserveRequest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(testRequestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", notNullValue());
    }

    @DisplayName("예약 조회 성공 시 200 코드와 조회한 예약의 날짜, 시간, 예약자 이름 반환")
    @Test
    void findReservationRequest() {
        int generatedId = requestReserveAndGetGeneratedId();

        RestAssured.given().log().all()
                .get("/reservations/" + generatedId)
                .then().log().all()
                .body("id", is(generatedId))
                .body("date", is(testRequestDto.getDate().format(dateFormatter)))
                .body("time", is(testRequestDto.getTime().format(timeFormatter)))
                .body("name", is(testRequestDto.getName()))
                .body("themeName", is(DEFAULT_THEME.getName()))
                .body("themeDesc", is(DEFAULT_THEME.getDesc()))
                .body("themePrice", is(DEFAULT_THEME.getPrice()));
    }

    @DisplayName("예약 취소 성공 시 204 코드 반환")
    @Test
    void cancelReservationRequest() {
        int generatedId = requestReserveAndGetGeneratedId();

        RestAssured.given().log().all()
                .delete("/reservations/" + generatedId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("중복 예약 요청 시 400 코드 반환")
    @Test
    void duplicateReserveRequest() {
        requestReserveAndGetGeneratedId();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(testRequestDto)
                .post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private int requestReserveAndGetGeneratedId() {
        String location = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(testRequestDto)
                .post("/reservations")
                .then().log().all()
                .extract().header("Location");

        String[] split = location.split("/");
        return Integer.parseInt(split[split.length - 1]);
    }
}
