package nextstep.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.CreateThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE RESERVATION\n" +
                "(\n" +
                "    id          bigint not null auto_increment,\n" +
                "    date        date,\n" +
                "    time        time,\n" +
                "    name        varchar(20),\n" +
                "    theme_id    bigint not null,\n" +
                "    primary key (id)\n" +
                ");");

        jdbcTemplate.execute("DROP TABLE theme IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE theme\n" +
                "(\n" +
                "    id    bigint not null auto_increment,\n" +
                "    name  varchar(20),\n" +
                "    desc  varchar(255),\n" +
                "    price int,\n" +
                "    primary key (id)\n" +
                ");");
    }

    @Test
    void 테마_생성_요청() {
        // given
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마1", "테마내용입니다", 19000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)

        // when
                .when().post("/themes")


        // then
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void id로_테마_조회() {
        // given
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마1", "테마내용입니다", 19000);
        ExtractableResponse<Response> response = createTheme(themeRequest);
        String expected = response.header("Location").split("/")[2];
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        // when
                .when().get(response.header("Location"))

        // then
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(Integer.valueOf(expected)))
                .body("name", equalTo(themeRequest.getName()))
                .body("desc", equalTo(themeRequest.getDesc()))
                .body("price", equalTo(themeRequest.getPrice()));
    }

    @Test
    void 모든_테마_조회() {
        // given
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마1", "테마내용입니다", 19000);
        createTheme(themeRequest);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        // when
                .when().get("/themes")

        // then
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void id로_테마_삭제_요청() {
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마1", "테마내용입니다", 19000);
        ExtractableResponse<Response> response = createTheme(themeRequest);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

                // when
                .when().delete(response.header("Location"))

                // then
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 존재하지_않는_테마를_조회하면_예외가_발생한다() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

                // when
                .when().get("/themes/1000")

                // then
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 예약된_테마를_삭제하면_예외가_발생한다() {
        // given
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마1", "테마내용입니다", 19000);
        ExtractableResponse<Response> response = createTheme(themeRequest);
        Long themeId = Long.parseLong(response.header("Location").split("/")[2]);

        CreateReservationRequest reservationRequest = new CreateReservationRequest(
                "2022-02-02",
                "13:00",
                "davi",
                themeId);
        createReservation(reservationRequest);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        // when
                .when().delete("/themes/" + themeId)

        // then
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> createTheme(CreateThemeRequest themeRequest) {
        return RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when()
                .post("/themes")
                .then()
                .extract();
    }

    private ExtractableResponse<Response> createReservation(CreateReservationRequest createReservationRequest) {
        return RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createReservationRequest)
                .when()
                .post("/reservations")
                .then()
                .extract();
    }
}
