package web.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import web.dto.request.ThemeRequestDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ThemeControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("테마 생성을 성공하면 201 반환")
    @Test
    @Order(1)
    void createTheme() {
        ThemeRequestDTO themeRequestDTO = new ThemeRequestDTO("우주테마", "우주선을 몰아서 지구로 가는 테마", 40000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDTO)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/1");
    }

    @DisplayName("이름이 같은 테마를 생성하면 409 반환")
    @Test
    @Order(2)
    void createDuplicatedTheme() {
        ThemeRequestDTO themeRequestDTO = new ThemeRequestDTO("우주테마", "우주테마입니다.", 50000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDTO)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }
}
