package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.dto.ReservationRequest;
import nextstep.dto.ThemeRequest;
import nextstep.repository.ReservationJdbcTemplateDao;
import nextstep.repository.ThemeJdbcTemplateDao;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

@DisplayName("Theme Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThemeControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    ThemeService themeService;
    @Autowired
    ThemeJdbcTemplateDao themeJdbcTemplateDao;
    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationJdbcTemplateDao reservationJdbcTemplateDao;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        saveSampleTheme("테마이름", "테마설명", 22000);
    }

    private void saveSampleTheme(String name, String desc, int price) {
        ThemeRequest requestDto = new ThemeRequest(name, desc, price);
        themeService.create(requestDto);
    }

    @AfterEach
    void afterEach() {
        reservationJdbcTemplateDao.clear();
        themeJdbcTemplateDao.clear();
    }

    @DisplayName("생성 - 정상적인 양식으로 요청시 성공해야 한다.")
    @Test
    void createNormally() {
        Map<String, String> request = new HashMap<>() {{
            put("name", "테마이름2");
            put("desc", "테마설명");
            put("price", "22000");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/2");
    }

    @DisplayName("생성 - 잘못된 양식(값)으로 요청시 예외처리 되어야 한다.")
    @ParameterizedTest
    @CsvSource(value = {
            ";테마설명;22000",
            "테마이름2;;22000",
            "테마이름2;테마설명;",
            "테마이름2;테마설명;-1",
            "테마이름2;테마설명;a"
    }, delimiter = ';')
    void createByInvalidValue(String name, String desc, String price) {
        Map<String, String> request = new HashMap<>() {{
            put("name", name);
            put("desc", desc);
            put("price", price);
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("생성 - 이미 등록된 테마이름으로 요청시 예외처리 되어야 한다.")
    @Test
    void createByAlreadyCreatedName() {
        Map<String, String> request = new HashMap<>() {{
            put("name", "테마이름");
            put("desc", "테마설명");
            put("price", "22000");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @DisplayName("조회(단일) - 등록된 테마의 id 로 요청시 조회 되어야 한다.")
    @Test
    void retrieveOneNormally() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("name", is("테마이름"))
                .body("desc", is("테마설명"))
                .body("price", is(22000));
    }

    @DisplayName("조회(단일) - 등록되지 않은 id 로 요청시 예외처리 되어야 한다.")
    @Test
    void retrieveOneByInvalidId() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/2")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("조회(단일) - 잘못된 id 로 요청시 예외처리 되어야 한다.")
    @Test
    void retrieveOneByWrongParameter() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/a")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("조회(목록) - 목록 조회 요청시 조회 되어야 한다.")
    @Test
    void retrieveAllNormally() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @DisplayName("수정 - 등록되어있는 id 와 올바른 theme 정보로 요청시 수정이 되어야 한다.")
    @Test
    void updateNormally() {
        Map<String, String> request = new HashMap<>() {{
            put("name", "테마이름2");
            put("desc", "테마설명2");
            put("price", "23000");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("name", is("테마이름2"))
                .body("desc", is("테마설명2"))
                .body("price", is(23000));
    }

    @DisplayName("수정 - 등록되지 않은 id 와 theme 정보로 요청시 새롭게 생성이 되어야 한다.")
    @Test
    void updateByNotCreatedId() {
        Map<String, String> request = new HashMap<>() {{
            put("name", "테마이름2");
            put("desc", "테마설명2");
            put("price", "23000");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/themes/100")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/2");
    }

    @DisplayName("수정 - 유효하지 않은 id 로 요청시 예외처리 되어야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "abc"})
    void updateByInvalidId(String invalidId) {
        Map<String, String> request = new HashMap<>() {{
            put("name", "테마이름2");
            put("desc", "테마설명2");
            put("price", "23000");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/themes/" + invalidId)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("수정 - 이미 등록되어있는 테마 이름으로 요청시 예외처리 되어야 한다.")
    @Test
    void updateByAlreadyCreatedName() {
        saveSampleTheme("테마이름2", "테마설명2", 22000);

        Map<String, String> request = new HashMap<>() {{
            put("name", "테마이름");
            put("desc", "테마설명2");
            put("price", "23000");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/themes/2")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @DisplayName("수정 - 해당하는 테마로 예약된 내역이 있다면 예외처리 되어야 한다.")
    @Test
    void updateByIdWithRegisteredReservation() {
        ReservationRequest requestDto = new ReservationRequest(
                LocalDate.parse("2023-03-10"),
                LocalTime.parse("13:00"),
                "jay",
                1L
        );
        reservationService.reserve(requestDto);

        Map<String, String> request = new HashMap<>() {{
            put("name", "테마이름2");
            put("desc", "테마설명2");
            put("price", "23000");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @DisplayName("삭제 - 요청이 정상적으로 이루어져야 한다")
    @Test
    void deleteNormally() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("삭제 - 존재하지 않는 id 로 요청시 예외처리 되어야 한다")
    @Test
    void deleteByUnregisteredId() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/2")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("삭제 - 유효하지 않은 id 로 요청시 예외처리 되어야 한다")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "abc"})
    void deleteByInvalidId(String invalidId) {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/" + invalidId)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("삭제 - 해당하는 테마로 예약된 내역이 있다면 예외처리 되어야 한다.")
    @Test
    void deleteByIdWithRegisteredReservation() {
        ReservationRequest requestDto = new ReservationRequest(
                LocalDate.parse("2023-03-10"),
                LocalTime.parse("13:00"),
                "jay",
                1L
        );
        reservationService.reserve(requestDto);

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }
}