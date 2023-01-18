package nextstep.repository.integration;

import io.restassured.RestAssured;
import nextstep.dto.web.request.CreateThemeRequest;
import nextstep.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static nextstep.domain.Theme.DEFAULT_THEME;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringStartsWith.startsWith;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ThemeIntegrationTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
        databaseCleaner.insertInitialData();
    }

    @Test
    void should_haveCreatedLocationAtHeader_when_createRequestSent() {
        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(CreateThemeRequest.of("테마이름", "테마설명", 10_000))
            .when()
                .post("/themes")
            .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", startsWith("/themes/"));
    }

    @Test
    void should_returnAllThemes_when_findAllRequestSent() {
        RestAssured
            .when()
                .get("/themes")
            .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("size()", is(1))
                .body("[0].name", is(DEFAULT_THEME.getName()))
                .body("[0].desc", is(DEFAULT_THEME.getDesc()))
                .body("[0].price", is(DEFAULT_THEME.getPrice()));

    }

    @Test
    void should_deleteTheme_when_deleteRequestSent() {
        RestAssured
            .when()
                .delete("/themes/1")
            .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
