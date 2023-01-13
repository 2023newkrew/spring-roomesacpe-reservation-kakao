package nextstep.main.java.nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.domain.ThemeCreateRequestDto;
import nextstep.main.java.nextstep.service.ThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
        Mockito.when(themeService.createTheme(themeCreateRequestDto))
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
}
