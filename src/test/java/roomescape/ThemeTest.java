package roomescape;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import roomescape.theme.dto.ThemeDto;

import static org.hamcrest.Matchers.hasItem;

@DisplayName("Theme Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeTest {
    @LocalServerPort
    int port;

    /**
     * - [ ] 테마 생성
     * - [ ] 테마 목록 조회
     * - [ ] 테마 삭제
     */
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private ValidatableResponse createTheme(ThemeDto themeDto) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeDto)
                .when().post("/themes")
                .then().log().all();
    }

    @Test
    @DisplayName("테마 생성")
    void createTheme() {
        ValidatableResponse response = createTheme(new ThemeDto("워너고홈", "병맛 어드벤처 회사 코믹물", 29000));
        response.statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("테마 목록 조회")
    void showTheme() {
        createTheme(new ThemeDto("워너고홈", "병맛 어드벤처 회사 코믹물", 29000));
        createTheme(new ThemeDto("무인도 탈출", "무인도에서 탈출하기", 50000));

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", hasItem("워너고홈"))
                .body("name", hasItem("무인도 탈출"));

    }

}
