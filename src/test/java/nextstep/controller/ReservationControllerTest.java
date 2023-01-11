package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.dto.ReservationRequest;
import nextstep.repository.ReservationJdbcTemplateDao;
import nextstep.service.ReservationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;


@DisplayName("Reservation Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationJdbcTemplateDao reservationJdbcTemplateDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        ReservationRequest requestDto = new ReservationRequest(
                LocalDate.parse("2023-01-10"),
                LocalTime.parse("13:00"),
                "jay"
        );
        reservationService.reserve(requestDto);
    }

    @AfterEach
    void afterEach() {
        reservationJdbcTemplateDao.clear();
    }

    @DisplayName("예약 - 예약되어있지 않은 날짜와 시간으로 요청시 예약이 성공해야 한다")
    @Test
    void reserveNotDuplicated() {
        Map<String, String> request = new HashMap<>() {{
            put("date", "2023-01-11");
            put("time", "13:00");
            put("name", "jay");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/2");
    }

    @DisplayName("예약 - 이미 예약된 날짜와 시간으로 요청시 예외처리 되어야 한다")
    @Test
    void reserveDuplicated() {
        Map<String, String> request = new HashMap<>() {{
            put("date", "2023-01-10");
            put("time", "13:00");
            put("name", "jay");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value())
                .body(is("이미 예약된 테마이거나 날짜/시간 입니다."));
    }

    @DisplayName("예약 - TimeTable 에 존재하지 않는 시간으로 요청시 예외처리 되어야 한다")
    @Test
    void reserveInvalidTime() {
        Map<String, String> request = new HashMap<>() {{
            put("date", "2023-01-11");
            put("time", "01:00");
            put("name", "jay");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("예약이 불가능한 시간입니다."));
    }

    @DisplayName("예약 - 잘못된 양식(값)으로 요청시 예외처리 되어야 한다")
    @ParameterizedTest
    @CsvSource(value = {
            ";14:30;jack",
            "abc;14:30;jack",
            "2023-02-01;;jack",
            "2023-02-01;abc;jack",
            "2023-02-01;14:30;"
    }, delimiter = ';')
    void reserveInvalidInputValue(String date, String time, String name) {
        Map<String, String> request = new HashMap<>() {{
            put("date", date);
            put("time", time);
            put("name", name);
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 - 잘못된 양식(키)으로 요청시 예외처리 되어야 한다")
    @ParameterizedTest
    @CsvSource(value = {
            "dating;time;name",
            "date;timing;name",
            "date;time;naming"
    }, delimiter = ';')
    void reserveInvalidInputKey(String date, String time, String name) {
        Map<String, String> request = new HashMap<>() {{
            put(date, "2023-02-01");
            put(time, "13:00");
            put(name, "jack");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("잘못된 입력 입니다."));
    }

    @DisplayName("조회 - 등록되어있는 id 로 조회시 조회 되어야 한다")
    @Test
    void retrieveNormally() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("date", is("2023-01-10"))
                .body("time", is("13:00:00"))
                .body("name", is("jay"))
                .body("themeName", is("워너고홈"))
                .body("themeDesc", is("병맛 어드벤처 회사 코믹물"))
                .body("themePrice", is(29000));
    }

    @DisplayName("조회 - 등록되지 않은 id 로 조회시 예외처리 되어야 한다")
    @Test
    void retrieveByUnregisteredId() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/2")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("조회 - 숫자가 아닌 값의 id 로 조회시 예외처리 되어야 한다")
    @Test
    void retrieveByInvalidId() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/abc")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("취소 - 요청이 정상적으로 이루어져야 한다")
    @Test
    void deleteNormally() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("취소 - 등록되지 않은 id 로 요청시 예외처리 되어야 한다")
    @Test
    void deleteByUnregisteredId() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/2")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("취소 - 숫자가 아닌 값의 id 로 요청시 예외처리 되어야 한다")
    @Test
    void deleteByInvalidId() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/abc")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}