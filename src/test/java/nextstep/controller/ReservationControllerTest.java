package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.IntegrationTest;
import nextstep.domain.Theme;
import nextstep.dto.ReservationRequest;
import nextstep.repository.ThemeJdbcTemplateDao;
import nextstep.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;


@DisplayName("Reservation Test")
@IntegrationTest
public class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    ReservationService reservationService;
    @Autowired
    ThemeJdbcTemplateDao themeJdbcTemplateDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        Theme theme = new Theme(
                "워너고홈",
                "병맛 어드벤처 회사 코믹물",
                29000
        );
        themeJdbcTemplateDao.save(theme);
        ReservationRequest requestDto = new ReservationRequest(
                LocalDate.parse("2023-03-10"),
                LocalTime.parse("13:00"),
                "jay",
                1L
        );
        reservationService.reserve(requestDto);
    }

    @DisplayName("예약 - 예약되어있지 않은 날짜와 시간으로 요청시 예약이 성공해야 한다")
    @Test
    void reserveNormally() {
        String currentThread = Thread.currentThread().getThreadGroup().getName() + " " + Thread.currentThread().getName() + " ";
        System.out.println("currentThread = " + currentThread);
        System.out.println("port = " + port);
        Map<String, String> request = new HashMap<>() {{
            put("date", "2023-03-11");
            put("time", "13:00");
            put("name", "jay");
            put("theme_id", "1");
        }};

        int port1 = RestAssured.port;
        System.out.println("port1 = " + port1);
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
    void reserveByDuplicatedDateAndTime() {
        Map<String, String> request = new HashMap<>() {{
            put("date", "2023-03-10");
            put("time", "13:00");
            put("name", "jay");
            put("theme_id", "1");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value())
                .body(is("이미 예약된 테마이거나 날짜/시간입니다."));
    }

    @DisplayName("예약 - TimeTable 에 존재하지 않는 시간으로 요청시 예외처리 되어야 한다")
    @Test
    void reserveByInvalidTime() {
        Map<String, String> request = new HashMap<>() {{
            put("date", "2023-03-11");
            put("time", "01:00");
            put("name", "jay");
            put("theme_id", "1");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("예약이 불가능한 시간입니다."));
    }

    @DisplayName("예약 - 과거의 날짜로 요청시 예외처리 되어야 한다")
    @Test
    void reserveByPastDate() {
        Map<String, String> request = new HashMap<>() {{
            put("date", "2023-01-01");
            put("time", "01:00");
            put("name", "jay");
            put("theme_id", "1");
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
            ";14:30;jack;1",
            "abc;14:30;jack;1",
            "2023-03-01;;jack;1",
            "2023-03-01;abc;jack;1",
            "2023-03-01;14:30;;1",
            "2023-03-01;14:30;jack;"
    }, delimiter = ';')
    void reserveByInvalidInputValue(String date, String time, String name, String themeId) {
        Map<String, String> request = new HashMap<>() {{
            put("date", date);
            put("time", time);
            put("name", name);
            put("theme_id", themeId);
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
            "dating;time;name;theme_id",
            "date;timing;name;theme_id",
            "date;time;naming;theme_id",
            "date;time;name;theme_ing"
    }, delimiter = ';')
    void reserveByInvalidInputKey(String date, String time, String name, String themeId) {
        Map<String, String> request = new HashMap<>() {{
            put(date, "2023-03-01");
            put(time, "13:00");
            put(name, "jack");
            put(themeId, "1");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("잘못된 입력입니다."));
    }

    @DisplayName("예약 - 존재하지 않은 테마 id 로 예약 요청시 예외처리 되어야 한다.")
    @Test
    void reserveByInvalidThemeId() {
        Map<String, String> request = new HashMap<>() {{
            put("date", "2023-03-01");
            put("time", "13:00");
            put("name", "jack");
            put("themeId", "2");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
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
                .body("date", is("2023-03-10"))
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

    @DisplayName("조회 - 유효하지 않은 id 로 조회시 예외처리 되어야 한다")
    @ParameterizedTest
    @ValueSource(strings = {"abc", "-1"})
    void retrieveByInvalidId(String id) {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/" + id)
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