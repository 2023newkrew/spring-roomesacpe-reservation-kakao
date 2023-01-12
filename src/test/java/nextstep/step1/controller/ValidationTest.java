package nextstep.step1.controller;

import io.restassured.RestAssured;
import nextstep.step1.RoomEscapeWebApplication;
import nextstep.step1.dto.ReservationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RoomEscapeWebApplication.class)
class ValidationTest {
    public static final String RESERVATION_DATE = "2022-11-22";

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp(){
        RestAssured.port = port;
    }

    @DisplayName("이름은 빈칸이 될 수 없다.")
    @Test
    void test1(){
        ReservationDto reservationDto = new ReservationDto(RESERVATION_DATE, "16:01", "", null);
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("날짜 형식은 yyyy-mm-dd이다.")
    @ParameterizedTest
    @ValueSource(strings = {"202-03-10", "2022-18-09", "2022-9-10", "2022-10-800", "2022:03-05", "2022-03:05"})
    void test2(String date){
        ReservationDto reservationDto = new ReservationDto(date, "16:02", "java", null);
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("시간 형식은 HH:mm이다.")
    @ParameterizedTest
    @ValueSource(strings = {"99:10", "24:99", "1:30", "24:1", "24-25"})
    void test3(String time){
        ReservationDto reservationDto = new ReservationDto(RESERVATION_DATE, time, "java", null);
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
