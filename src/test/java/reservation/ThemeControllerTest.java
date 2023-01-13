package reservation;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import reservation.dto.request.ThemeRequest;

import static org.hamcrest.core.Is.is;

@Sql({"schema.sql", "data.sql"})
@DisplayName("Theme Controller Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void setDown() {
        String[] sqls = {"DELETE FROM reservation", "DELETE FROM theme"};
        for (String sql : sqls) {
            jdbcTemplate.update(sql);
        }
    }

    @DisplayName("POST /themes")
    @Test
    void createThemes() {
        ThemeRequest themeRequest = new ThemeRequest("SCARY THEME", "I HATE JUMP SCARE", 30_000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/2");
    }

    @DisplayName("GET /themes/1")
    @Test
    void getThemes() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("name", is("워너고홈"));
    }

    @DisplayName("DELETE /themes/2 → GET /themes/2")
    @Test
    void deleteThemes() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_PLAIN_VALUE)
                .when().delete("/themes/2")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .body(is(""));

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/2")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
