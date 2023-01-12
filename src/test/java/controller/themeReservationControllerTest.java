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
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @DisplayName("예약 성공 시 상태코드는 CREATED이다.")
    void test1(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "16:01");
        getValidationResponse(reservationDto, HttpMethod.POST, RESERVATION_URL)
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("이미 예약된 날짜/시간에 예약하는 경우, 상태 코드는 INTERNAL_SERVER_ERROR이다.")
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
                .body("time", equalTo(reservationDto.getTime().toString()))
                .body("date", equalTo(reservationDto.getDate().toString()))
                .body("name", equalTo(reservationDto.getName()));
    }

    @Test
    @DisplayName("존재하지 않은 예약 정보를 요청할 경우 상태 코드는 NO_CONTENT이다.")
    void test4(){
        getValidationResponse(EMPTY_BODY, HttpMethod.GET, RESERVATION_URL + "/" + NOT_EXIST_RESERVATION_ID)
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("존재하지 않는 예약을 취소할 경우, 상태 코드는 NOT_FOUND이다.")
    void test5(){
        getValidationResponse(EMPTY_BODY, HttpMethod.DELETE, RESERVATION_URL + "/" +  NOT_EXIST_RESERVATION_ID)
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("취소한 예약은 조회할 수 없어야 한다.")
    void test6(){
        ReservationDto reservationDto = makeRandomReservationDto(RESERVATION_DATE, "16:08");

        String location = sendRequest(reservationDto, HttpMethod.POST, RESERVATION_URL)
                .getHeader(HttpHeaders.LOCATION);

        sendRequest(EMPTY_BODY, HttpMethod.DELETE, location);

        getValidationResponse(EMPTY_BODY, HttpMethod.GET, location)
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름은 빈칸이 될 수 없다.")
    void test7(String name){
        ReservationDto reservationDto = new ReservationDto(LocalDate.parse(RESERVATION_DATE), LocalTime.parse("16:01"), name, null);

        getValidationResponse(reservationDto, HttpMethod.POST, RESERVATION_URL)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    ReservationDto makeRandomReservationDto(String date, String time){
        List<String> names = List.of("omin", "ethan", "java");
        int randomIndex = ThreadLocalRandom.current().nextInt(names.size());

        return new ReservationDto(LocalDate.parse(date), LocalTime.parse(time), names.get(randomIndex), null);
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
