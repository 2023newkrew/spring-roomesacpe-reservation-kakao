package nextstep.main.java.nextstep.controller;

import io.restassured.RestAssured;
import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.domain.ThemeCreateRequestDto;
import nextstep.main.java.nextstep.service.ThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeControllerTest {
    @LocalServerPort
    int port;
    @MockBean
    private ThemeService themeService;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("테마 생성 POST method 테스트")
    void createThemeTest() {
        ThemeCreateRequestDto themeCreateRequestDto = new ThemeCreateRequestDto("name", "desc", 22000);
        Theme expectedTheme = new Theme(1L, themeCreateRequestDto.getName(), themeCreateRequestDto.getDesc(), themeCreateRequestDto.getPrice());
        when(themeService.createTheme(themeCreateRequestDto))
                .thenReturn(expectedTheme);

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeCreateRequestDto)
                .when()
                .post("/themes")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value())
                .header("location", "/themes/1");
    }

    @Test
    @DisplayName("테마 목록 조회 GET method 테스트")
    void findAllThemeTest() {
        List<Theme> themes = List.of(new Theme(1L, "theme1", "desc1", 1000),
                new Theme(2L, "theme2", "desc2", 2000),
                new Theme(3L, "theme3", "desc3", 3000));
        when(themeService.findAllTheme()).thenReturn(themes);

        RestAssured.given()
                .log()
                .all()
                .when()
                .get("themes")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(themes.size()));
    }

    @Test
    @DisplayName("테마 삭제 DELETE method 테스트")
    void deleteThemeByIdTest() {
        RestAssured.given()
                .log()
                .all()
                .when()
                .delete("themes/1")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
    }
}
