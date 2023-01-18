package roomescape;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import roomescape.reservation.dto.ReservationDto;
import roomescape.theme.dto.ThemeDto;

import static org.hamcrest.core.Is.is;

@DisplayName("Reservation Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationTest {
    @LocalServerPort
    int port;

    @BeforeAll
    void setUp() {
        RestAssured.port = port;

        //테마 생성
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ThemeDto("테스트", "예약테스트용 테마", 1000))
                .when().post("/themes").then();

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ThemeDto("테스트2", "예약테스트용 테마2", 2000))
                .when().post("/themes").then();
    }

    private ValidatableResponse createReservation(ReservationDto reservationDto) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all();
    }

    private String getId(ValidatableResponse response) {
        return response.extract().headers().getValue("Location").split("/")[2];
    }

    @DisplayName("예약 생성")
    @Test
    void createReservation() {
        createReservation(new ReservationDto("2022-08-13", "13:00", "kayla", 1L))
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("예약 시간이 같을 경우 생성 거졀")
    void rejectCreateReservation() {
        createReservation(new ReservationDto("2022-01-01", "13:00", "kayla", 1L));
        createReservation(new ReservationDto("2022-01-01", "13:00", "jerrie", 2L))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 조회")
    @Test
    void showReservation() {
        String id = getId(createReservation(new ReservationDto("2000-12-25", "12:00", "abcd", 1L)));
        RestAssured.given().log().all()
                .when().get("/reservations/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("abcd"));
    }

    @DisplayName("예약 아이디가 존재하지 않을 경우 조회 거절")
    @Test
    void rejectShowReservation() {
        RestAssured.given().log().all()
                .when().get("/reservations/1000")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        //create
        String id = getId(createReservation(new ReservationDto("1999-12-31", "10:00", "qwer", 1L)));

        RestAssured.given().when().get("/reservations/" + id).then()
                .body("name", is("qwer"));

        //delete
        RestAssured.given().log().all()
                .when().delete("/reservations/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        //check
        RestAssured.given().when().get("/reservations/" + id).then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 아이디가 존재하지 않을 경우 삭제 거절")
    @Test
    void rejectDeleteReservation() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1000")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
