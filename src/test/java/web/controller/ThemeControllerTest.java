package web.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import web.dto.request.ThemeRequestDTO;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ThemeControllerTest {

    private Long themeId;

    @BeforeEach
    void setUp() {
        ThemeRequestDTO themeRequestDTO = new ThemeRequestDTO("좀비테마", "좀비 소굴을 탈출하는 테마", 30000);

        ExtractableResponse<Response> themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDTO)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        String[] themeLocation = themeResponse.header("Location").split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);
    }

    @DisplayName("테마 생성을 성공하면 201 반환")
    @Test
    void createTheme() {
        ThemeRequestDTO themeRequestDTO = new ThemeRequestDTO("우주테마", "우주선을 몰아서 지구로 가는 테마", 40000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDTO)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("이름이 같은 테마를 생성하면 409 반환")
    @Test
    void createDuplicatedTheme() {
        ThemeRequestDTO themeRequestDTO = new ThemeRequestDTO("좀비테마", "좀비 소굴을 탈출하는 테마", 40000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDTO)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @DisplayName("테마 목록 조회를 성공하면 200 반환")
    @Test
    void findAllThemes() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    @DisplayName("테마 수정을 성공하면 200 반환")
    @Test
    void changeTheme() {
        ThemeRequestDTO changethemeRequestDTO = new ThemeRequestDTO("좀비테마수정", "좀비테마 수정입니다.", 50000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(changethemeRequestDTO)
                .when().put("/themes/" + themeId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("없는 테마를 수정하면 404 반환")
    @Test
    void changeNotExistingTheme() {
        ThemeRequestDTO themeRequestDTO = new ThemeRequestDTO("좀비테마수정", "좀비테마 수정입니다.", 50000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDTO)
                .when().put("/themes/99")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("테마 삭제를 성공하면 204 반환")
    @Test
    void deleteTheme() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/" + themeId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("없는 테마을 삭제하면 404를 반환")
    @Test
    void deleteNotExistingTheme() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/99")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
