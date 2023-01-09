package nextstep.mapping;

import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import nextstep.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@DisplayName("웹 요청 / 응답 처리로 입출력 추가")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationTest {

    @LocalServerPort
    int port;

    private static Stream<Arguments> getCreateReservationData() {
        return Stream.of(Arguments.of("2022-08-11", "13:00", "name"));
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약 하기")
    @Order(1)
    @Test
    void createReservation() {
        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00:00"), "name", null);

        RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation).when().post("/reservations").then().log().all()
                .statusCode(HttpStatus.CREATED.value()).header("Location", "/reservations/1");
    }

    @DisplayName("예약 조회")
    @Order(2)
    @Test
    void showReservation() {
        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-12"),
                LocalTime.parse("13:00:00"), "name", null);

        String location = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation).post("/reservations").thenReturn().header("Location");

        RestAssured.given().log().all().accept(MediaType.APPLICATION_JSON_VALUE).when()
                .get(location).then().log().all().statusCode(HttpStatus.OK.value())
                .body("name", is("name")).body("date", is("2022-08-12"))
                .body("time", is("13:00:00"));
    }

    @DisplayName("예약 취소")
    @Order(3)
    @Test
    void deleteReservation() {
        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-13"),
                LocalTime.parse("13:00:00"), "name", null);

        String location = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation).post("/reservations").thenReturn().header("Location");

        RestAssured.given().log().all().accept(MediaType.APPLICATION_JSON_VALUE).when()
                .delete(location).then().log().all().statusCode(HttpStatus.NO_CONTENT.value());
    }
}
