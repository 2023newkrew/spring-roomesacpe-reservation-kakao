package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import nextstep.domain.dto.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThemeControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createTheme() {
        ThemeRequest themeRequest = new ThemeRequest("theme1", "desc1", 10000);
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(themeRequest)
                .when()
                    .post("/themes")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void getTheme() {
        ThemeRequest themeRequest1 = new ThemeRequest("theme1", "desc1", 10000);
        ThemeRequest themeRequest2 = new ThemeRequest("theme2", "desc2", 20000);
        RestAssured
                .given().contentType(ContentType.JSON).body(themeRequest1)
                .when().post("/themes");
        RestAssured
                .given().contentType(ContentType.JSON).body(themeRequest2)
                .when().post("/themes");
        RestAssured
                .given().accept(ContentType.JSON)
                .when().get("/themes")
                .then().log().body().statusCode(HttpStatus.OK.value());
    }

    @Test
    void deleteTheme() {
        ThemeRequest themeRequest = new ThemeRequest("theme1", "desc1", 10000);
        RestAssured
                .given().contentType(ContentType.JSON).body(themeRequest)
                .when().post("/themes");
        RestAssured
                .given().log().all()
                .when().delete("/themes/1").then()
                .statusCode(org.apache.http.HttpStatus.SC_NO_CONTENT);
    }
}