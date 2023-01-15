package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.domain.dto.theme.CreateThemeDto;
import nextstep.domain.dto.theme.UpdateThemeDto;
import nextstep.domain.theme.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Theme Controller 테스트")
// 매 테스트 전 해당 경로의 스크립트 실행
@Sql(scripts = {"classpath:recreate.sql"})
public class ThemeControllerTest {

    @LocalServerPort
    int port;

    private static final CreateThemeDto createThemeDto = new CreateThemeDto(
            "카페 라떼",
            "LIH 바이러스 위기에서 탈출",
            32000
    );

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("테마 생성하면 201 status와 id를 포함한 Location을 응답")
    @Test
    void createTheme() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createThemeDto)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/2");
    }

    @DisplayName("가격을 음수로 설정시 400 status 응답")
    @Test
    void negativePriceThemeCreateTest() {
        CreateThemeDto newTheme = new CreateThemeDto(
                "카페 라떼",
                "LIH 바이러스 위기에서 탈출",
                -1
        );
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newTheme)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("전체 테마 조회 : 기존 1개 + 2개 추가 = 3개")
    @Test
    void findAllThemes() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createThemeDto)
                .when().post("/themes");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createThemeDto)
                .when().post("/themes");

        List<Theme> themeList = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/all")
                .then().log().all()
                .extract()
                .jsonPath().getList("",Theme.class);

        assertThat(themeList).hasSize(3);
    }

    @DisplayName("테마 업데이트 성공시 200 status 반환")
    @Test
    void updateTheme() {
        UpdateThemeDto updateThemeDto = new UpdateThemeDto(
                1l,
                "new name",
                "new desc",
                10000
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateThemeDto)
                .when().put("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("테마 삭제 성공시 204 status 응답")
    @Test
    void deleteTheme() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
