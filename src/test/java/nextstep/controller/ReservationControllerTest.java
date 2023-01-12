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

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RoomEscapeWebApplication.class)
@TestExecutionListeners(listeners = RepositoryTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class ReservationControllerTest {

    private static final Theme DEFAULT_THEME = new Theme("검은방", "밀실 탈출", 30_000);

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
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
        ReservationRequestDto reservationRequestDto =
                generateReservationRequestDto("2023-01-01", "13:00", "john");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", notNullValue());
    }

    @DisplayName("예약 조회 성공 시 200 코드와 조회한 예약의 날짜, 시간, 예약자 이름 반환")
    @Test
    void findReservationRequest() {
        ReservationRequestDto reservationRequestDto =
                generateReservationRequestDto("2023-01-01", "13:00", "john");

        String location = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequestDto)
                .post("/reservations")
                .then().log().all()
                .extract().header("Location");

        String[] split = location.split("/");
        int savedId = Integer.parseInt(split[split.length - 1]);

        RestAssured.given().log().all()
                .get("/reservations/" + savedId)
                .then().log().all()
                .body("id", is(savedId))
                .body("date", is("2023-01-01"))
                .body("time", is("13:00"))
                .body("name", is("john"))
                .body("themeName", is(DEFAULT_THEME.getName()))
                .body("themeDesc", is(DEFAULT_THEME.getDesc()))
                .body("themePrice", is(DEFAULT_THEME.getPrice()));
    }
}
