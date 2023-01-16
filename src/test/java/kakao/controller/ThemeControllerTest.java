package kakao.controller;

import io.restassured.RestAssured;
import kakao.Initiator;
import kakao.dto.request.CreateThemeRequest;
import kakao.dto.request.UpdateThemeRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThemeControllerTest {

    @LocalServerPort
    int port;

    Initiator initiator;

    private final String path = "themes";
    private final CreateThemeRequest createRequest = new CreateThemeRequest(
            "theme",
            "themeDesc",
            1000
    );

    @Autowired
    ThemeControllerTest(JdbcTemplate jdbcTemplate) {
        initiator = new Initiator(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void clear() {
        initiator.clear();
    }

    @DisplayName("예약이 정상적으로 생성되면 201 status를 반환한다")
    @Test
    @Transactional
    void createTheme() {
        RestAssured.given()
                .contentType("application/json")
                .body(createRequest)
                .when().post(path)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/1");
    }

    @DisplayName("중복된 이름의 예약을 생성하면 409 status를 반환한다")
    @Test
    @Transactional
    void createDuplicatedNameTheme() {
        RestAssured.given()
                .contentType("application/json")
                .body(createRequest)
                .when().post(path);

        RestAssured.given()
                .contentType("application/json")
                .body(createRequest)
                .when().post(path)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @DisplayName("저장된 모든 Theme을 조회한다")
    @Test
    void List() {
        RestAssured.given()
                .contentType("application/json")
                .body(createRequest)
                .when().post(path);

        RestAssured.given()
                .contentType("application/json")
                .when().get(path)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size", is(1));
    }

    @DisplayName("pathvariable의 id로 Theme을 조회하면 200 status를 반환한다")
    @Test
    void getTheme() {
        RestAssured.given()
                .contentType("application/json")
                .body(createRequest)
                .when().post(path);

        RestAssured.given()
                .contentType("application/json")
                .when().get(path + "/1")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("존재하지 않는 id로 Theme을 조회하면 404 status를 반환한다")
    @Test
    void getInvalidTheme() {
        RestAssured.given()
                .contentType("application/json")
                .when().get(path + "/1")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("UpdateThemeRequest로 patch하면 200 status와 업데이트된 resource를 response로 받는다")
    @Test
    void updateTheme() {
        RestAssured.given()
                .contentType("application/json")
                .body(createRequest)
                .when().post(path);

        UpdateThemeRequest updateRequest = UpdateThemeRequest.builder()
                .id(1L)
                .name("updatedThemeName")
                .desc("updatedDesc")
                .price(3000)
                .build();

        RestAssured.given()
                .contentType("application/json")
                .body(updateRequest)
                .when().patch(path)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("updatedThemeName"))
                .body("desc", is("updatedDesc"))
                .body("price", is(3000));
    }

    @DisplayName("존재하지 않는 id인 Request를 patch하면 404 status를 rseponse로 받는다")
    @Test
    void updateInvalidTheme() {
        UpdateThemeRequest updateRequest = UpdateThemeRequest.builder()
                .id(1L)
                .name("updatedThemeName")
                .desc("updatedDesc")
                .price(3000)
                .build();

        RestAssured.given()
                .contentType("application/json")
                .body(updateRequest)
                .when().patch(path)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
