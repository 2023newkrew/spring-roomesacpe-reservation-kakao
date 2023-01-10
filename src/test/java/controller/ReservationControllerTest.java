package controller;

import io.restassured.RestAssured;
import nextstep.RoomEscapeWebApplication;
import nextstep.dto.ReservationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RoomEscapeWebApplication.class)
class ReservationControllerTest {
    public static final String RESERVATION_DATE = "2022-11-22";
    public static final String RESERVATION_URL = "/reservations";

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp(){
        RestAssured.port = port;
    }

    @DisplayName("예약 되지 않은 날짜/시간에 예약을 한다.")
    @Test
    void test1(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "16:01");
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post(RESERVATION_URL)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("이미 존재하는 날짜/시간에 예약한다.")
    @Test
    void test2(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "16:02");
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post(RESERVATION_URL);


        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post(RESERVATION_URL)
                .then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }



    @Test
    @DisplayName("예약한 내용을 조회한다.")
    void test5(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "16:05");
        String location = RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post(RESERVATION_URL)
                .getHeader("location");

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(location)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("time", equalTo(reservationDto.getTime()))
                .body("name", equalTo(reservationDto.getName()))
                .body("date", equalTo(reservationDto.getDate()));
    }

    @Test
    @DisplayName("예약하지 않은 내용은 조회할 수 없다.")
    void test6(){
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(RESERVATION_URL + "/" +250)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("존재하지 않는 예약은 취소할 수 없다.")
    void test7(){
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(RESERVATION_URL + "/" + 250)
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("취소한 예약은 조회할 수 없어야 한다.")
    void test8(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "16:08");
        String location = RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post(RESERVATION_URL)
                .getHeader("location");

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(location)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(location)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    ReservationDto makeRandomReservationDto(String date, String time){
        List<String> names = List.of("omin", "ethan", "java");

        int index = ThreadLocalRandom.current().nextInt(3);

        return new ReservationDto(date, time, names.get(index), null);

    }
}
