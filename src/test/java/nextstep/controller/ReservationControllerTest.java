package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.domain.dto.reservation.CreateReservationDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Reservation Controller 테스트")
// 매 테스트 전 해당 경로의 스크립트 실행
@Sql(scripts = {"classpath:recreate.sql"})
public class ReservationControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약을 생성하면 201 status와 id를 포함한 Location을 응답")
    @Test
    void createReservation() {
        CreateReservationDto reservationDto = new CreateReservationDto("2022-08-11", "14:30:00", "name", 1l);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("중복된 시간에 예약시 400 status 응답")
    @Test
    void sameTimeReservationTest() {
        CreateReservationDto reservationDto = new CreateReservationDto("2022-08-11", "13:30:00", "name", 1l);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations");

        CreateReservationDto reservationDto2 = new CreateReservationDto("2022-08-11", "13:30:00", "name2", 1l);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto2)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약을 조회하면 200 status를 보내고 저장된 값과 요청한 값이 일치")
    @Test
    void getReservation() {
        CreateReservationDto reservationDto = new CreateReservationDto("2022-08-11", "13:30:00", "name", 1l);

        String location = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");

        long id = Long.parseLong(location.split("/")[2]);

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("id", is(1))
                .body("date", is("2022-08-11"))
                .body("time", is("13:30:00"))
                .body("name", is("name"))
                .body("themeName", is("워너고홈"))
                .body("themeDesc", is("병맛 어드벤처 회사 코믹물"))
                .body("themePrice", is(29_000));
    }

    @DisplayName("예약 삭제 성공시 204 status를 응답")
    @Test
    void deleteReservation() {
        CreateReservationDto reservationDto = new CreateReservationDto("2022-08-11", "13:30:00", "name", 1l);

        String location = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("Location");

        long id = Long.parseLong(location.split("/")[2]);

        RestAssured.given().log().all()
                .when().delete("/reservations/"+id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
