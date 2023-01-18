package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import roomescape.dto.ThemeRequestDto;

import java.time.LocalTime;

import static org.hamcrest.Matchers.startsWith;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "app.console.enabled=false")
public class ThemeControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("테마 생성 성공 테스트")
    @Test
    void createThemeSuccessTest() {
        String name = LocalTime.now().toString();  // 중복 방지
        String desc = LocalTime.now().toString();  // 중복 방지
        Integer price = 20_000;
        ThemeRequestDto req = new ThemeRequestDto(name, desc, price);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", res -> startsWith("/themes/"));
    }

    @DisplayName("테마 목록 조회 성공 테스트")
    @Test
    void findThemeSuccessTest() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
