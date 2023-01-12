package controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import nextstep.RoomEscapeWebApplication;
import nextstep.dto.ReservationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RoomEscapeWebApplication.class)
class themeReservationControllerTest {
    public static final String RESERVATION_DATE = "2022-11-22";
    public static final String RESERVATION_URL = "/reservations";
    public static final Long NOT_EXIST_RESERVATION_ID = Long.MAX_VALUE;
    public static final Object EMPTY_BODY = new ReservationDto();
    public static final String REQUEST_ACCEPT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;
    public static final String REQUEST_CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp(){
        RestAssured.port = port;
    }

    @Test
    @DisplayName("예약 되지 않은 날짜/시간에 예약을 한다.")
    void test1(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "16:01");
        getValidationResponse(reservationDto, HttpMethod.POST, RESERVATION_URL)
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("이미 예약된 날짜/시간에 예약한다.")
    void test2(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "16:02");
        sendRequest(reservationDto, HttpMethod.POST, RESERVATION_URL);

        getValidationResponse(reservationDto, HttpMethod.POST, RESERVATION_URL)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @DisplayName("예약 요청 정보가 온전히 저장되어야 한다.")
    void test3(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "16:05");
        String location = sendRequest(reservationDto, HttpMethod.POST, RESERVATION_URL)
                .getHeader(HttpHeaders.LOCATION);

        getValidationResponse(reservationDto, HttpMethod.GET, location)
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("time", equalTo(reservationDto.getTime()))
                .body("name", equalTo(reservationDto.getName()))
                .body("date", equalTo(reservationDto.getDate()));
    }

    @Test
    @DisplayName("예약하지 않은 내용은 조회할 수 없다.")
    void test4(){
        getValidationResponse(EMPTY_BODY, HttpMethod.GET, RESERVATION_URL + "/" + NOT_EXIST_RESERVATION_ID)
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("존재하지 않는 예약은 취소할 수 없다.")
    void test5(){
        getValidationResponse(EMPTY_BODY, HttpMethod.DELETE, RESERVATION_URL + "/" +  NOT_EXIST_RESERVATION_ID)
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("취소한 예약은 조회할 수 없어야 한다.")
    void test6(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "16:08");

        String location = sendRequest(reservationDto, HttpMethod.POST, RESERVATION_URL)
                .getHeader("location");

        getValidationResponse(EMPTY_BODY, HttpMethod.DELETE, location)
                .statusCode(HttpStatus.NO_CONTENT.value());

        getValidationResponse(EMPTY_BODY, HttpMethod.GET, location)
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("이름은 빈칸이 될 수 없다.")
    @Test
    void test7(){
        ReservationDto reservationDto = new ReservationDto(RESERVATION_DATE, "16:01", "", null);

        getValidationResponse(reservationDto, HttpMethod.POST, RESERVATION_URL)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("날짜 형식은 yyyy-mm-dd이다.")
    @ParameterizedTest
    @ValueSource(strings = {"202-03-10", "2022-18-09", "2022-9-10", "2022-10-800", "2022:03-05", "2022-03:05"})
    void test8(String date){
        ReservationDto reservationDto = makeRandomReservationDto(date, "16:08");

        getValidationResponse(reservationDto, HttpMethod.POST, RESERVATION_URL)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("시간 형식은 HH:mm이다.")
    @ParameterizedTest
    @ValueSource(strings = {"99:10", "24:99", "1:30", "24:1", "24-25"})
    void test9(String time){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, time);

        getValidationResponse(reservationDto, HttpMethod.POST, RESERVATION_URL)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    ReservationDto makeRandomReservationDto(String date, String time){
        List<String> names = List.of("omin", "ethan", "java");
        int randomIndex = ThreadLocalRandom.current().nextInt(names.size());

        return new ReservationDto(date, time, names.get(randomIndex), null);
    }

    private static Response sendRequest(Object body, HttpMethod httpMethod, String url) {
        return RestAssured.given().log().all()
                .accept(REQUEST_ACCEPT_MEDIA_TYPE)
                .contentType(REQUEST_CONTENT_TYPE)
                .body(body)
                .when().request(httpMethod.toString(), url);
    }

    private static ValidatableResponse getValidationResponse(Object body, HttpMethod httpMethod, String url) {
        return sendRequest(body, httpMethod, url)
                .then().log().all();
    }
}
