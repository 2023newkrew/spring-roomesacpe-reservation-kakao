package nextstep.roomescape.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.roomescape.controller.RequestDTO.ReservationRequestDTO;
import nextstep.roomescape.controller.RequestDTO.ThemeRequestDTO;
import nextstep.roomescape.repository.model.Theme;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    int port;
    private static Theme theme;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        theme = createTheme();
    }

    @AfterEach
    void afterEach() {
        deleteTheme(theme.getId());
    }

    @DisplayName("예약 생성")
    @Test
    void createReservation() {
        ReservationRequestDTO reservation = createRequest(LocalDate.parse("2099-01-01"));

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String id = response.response().getHeader("Location").split("/")[2];
        deleteReservation(Long.parseLong(id));

    }

    @DisplayName("예약 생성 예외처리")
    @Test
    void createReservationDuplicate() {
        ReservationRequestDTO reservation = createRequest(LocalDate.parse("2099-02-02"));
        Long id = createReservation(reservation);
        try {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(reservation)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(is("예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있습니다."));
        } finally {
            deleteReservation(id);
        }


    }


    @DisplayName("예약 검색")
    @Test
    void showReservation() {

        Long id = createReservation(createRequest(LocalDate.parse("2099-05-05")));
        try {
            RestAssured.given().log().all()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/reservations/" + id)
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value())
                    .body("date", is("2099-05-05"))
                    .body("time", is("13:00:00"));
        } finally {
            deleteReservation(id);
        }

    }


    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        Long id = createReservation(createRequest(LocalDate.parse("2099-04-04")));
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private ReservationRequestDTO createRequest(LocalDate date) {
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "kakao";
        return new ReservationRequestDTO(date, time, name, theme);
    }

    private Long createReservation(ReservationRequestDTO reservationRequestDTO) {
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return Long.parseLong(location.split("/")[2]);
    }

    private ExtractableResponse<Response> deleteReservation(Long id) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/" + id)
                .then().log().all()
                .extract();
    }

    public Theme createTheme() {
        ThemeRequestDTO body = new ThemeRequestDTO("테마이름", "테마설명", 22000);
        String location = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");
        return new Theme(Long.parseLong(location.split("/")[2]), "테마이름", "테마설명", 22000);
    }

    public void deleteTheme(Long id) {
        RestAssured
                .given().log().all()
                .when().delete("/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }
}