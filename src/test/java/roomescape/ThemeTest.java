package roomescape;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import roomescape.reservation.dto.ThemeDto;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

@DisplayName("Theme Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeTest {
    @LocalServerPort
    int port;

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

    private String getId(ValidatableResponse response) {
        return response.extract().headers().getValue("Location").split("/")[2];
    }

    @Test
    @DisplayName("테마 생성")
    void createTheme() {
        createTheme(new ThemeDto("워너고홈", "병맛 어드벤처 회사 코믹물", 29000))
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("테마 이름이 같을 경우 생성 거절")
    void rejectCreateTheme() {
        createTheme(new ThemeDto("나홀로집에", "크리스마스 코믹물", 30000));
        ValidatableResponse response = createTheme(new ThemeDto("나홀로집에", "공포물", 50000));
        response.statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("테마 목록 조회")
    void showAllThemes() {
        //create
        createTheme(new ThemeDto("정글 탈출", "정글에서 탈출하기", 30000));
        createTheme(new ThemeDto("무인도 탈출", "무인도에서 탈출하기", 50000));

        //check
        RestAssured.given().log().all()
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", hasItem("정글 탈출"))
                .body("name", hasItem("무인도 탈출"));
    }

    @Test
    @DisplayName("테마 삭제")
    void deleteTheme() {
        //create
        String id = getId(createTheme(new ThemeDto("비행기", "비행기에서 탈출하기", 50000)));

        //check
        RestAssured.given().when().get("/themes").then()
                .body("name", hasItem("비행기"));

        //delete
        RestAssured.given().log().all()
                .when().delete("/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        //check
        RestAssured.given().when().get("/themes").then()
                .body("name", not(hasItem("비행기")));
    }

    @Test
    @DisplayName("테마 아이디가 존재하지 않을 경우 삭제 거절")
    void rejectDeleteTheme() {
        RestAssured.given().log().all()
                .when().delete("/themes/1000")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
