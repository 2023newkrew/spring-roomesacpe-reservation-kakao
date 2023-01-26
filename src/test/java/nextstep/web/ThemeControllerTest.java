package nextstep.web;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.ReservationRequest;
import nextstep.dto.ThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.model.Theme;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcTemplateThemeRepository repository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM reservation");
        jdbcTemplate.execute("DELETE FROM theme");
    }
    @DisplayName("테마를 생성한다")
    @Test
    void createTheme() {
        ThemeRequest request = new ThemeRequest("방탈출 새로운 테마", "새로운 테마", 40200);

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/themes")
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();

        Long id = 샏성된_테마의_번호를_반환한다(response);
        Theme theme = repository.findById(id).orElseThrow();
        assertThat(theme.getName()).isEqualTo(request.getName());
        assertThat(theme.getDesc()).isEqualTo(request.getDesc());
        assertThat(theme.getPrice()).isEqualTo(request.getPrice());
    }

    @DisplayName("중복된 테마 생성시 예외 응답을 반환한다")
    @Test
    void createDuplicateTheme() {
        String name = "name_duplicated";
        String desc = "desc_";
        int price = 32000;
        테마_생성_후_번호를_반환한다(name, desc, price);

        ThemeRequest request = new ThemeRequest(name, desc, price);

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/themes")
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("테마를 조회한다")
    @Test
    void getTheme() {
        String name = "방탈출", desc = "꽁꽁 숨겨져있는 암호를 찾자";
        Integer price = 23000;
        Long id = 테마_생성_후_번호를_반환한다(name, desc, price);

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/themes/" + id)
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        ThemeResponse theme = response.as(ThemeResponse.class);
        assertThat(theme.getName()).isEqualTo(name);
        assertThat(theme.getDesc()).isEqualTo(desc);
        assertThat(theme.getPrice()).isEqualTo(price);
    }

    @DisplayName("존재하지 않는 테마를 조회할 경우 예외 응답을 반환한다")
    @Test
    void getNotFoundTheme() {
        Long id = 2343345L;

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/themes/" + id)
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.body()).isNotNull();

    }
    @DisplayName("테마를 삭제한다")
    @Test
    void deleteTheme() {
        String name = "방탈출", desc = "꽁꽁 숨겨져있는 암호를 찾자";
        Integer price = 19999;
        Long id = 테마_생성_후_번호를_반환한다(name, desc, price);

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/themes/" + id)
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("해당 테마에 예약이 존재하면 테마 삭제시 예외 응답을 반환한다")
    @Test
    void deleteReservationExistsTheme() {
        Long id = 테마_생성_후_번호를_반환한다("방탈출 테마 new", "새롭게 생겨난 테마!", 20000);
        예약_생성_후_번호를_반환한다("예약 이름", LocalDate.of(2023, 12, 12), LocalTime.of(11,21), id);

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/themes/" + id)
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(response.body()).isNotNull();
    }

    private Long 샏성된_테마의_번호를_반환한다(ExtractableResponse<Response> response) {
        String id = response
                .header(HttpHeaders.LOCATION)
                .split("/")[2];
        return Long.parseLong(id);
    }

    private Long 테마_생성_후_번호를_반환한다(String name, String desc, Integer price) {
        ThemeRequest request = new ThemeRequest(name, desc, price);

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/themes")
                .then()
                .extract();

        String id = response
                .header(HttpHeaders.LOCATION)
                .split("/")[2];
        return Long.parseLong(id);
    }

    private Long 예약_생성_후_번호를_반환한다(String name, LocalDate date, LocalTime time, Long themeId) {
        ReservationRequest request = new ReservationRequest(name, date, time, themeId);

        String id =  RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/reservations")
                .then().log().all()
                .extract()
                .header(HttpHeaders.LOCATION)
                .split("/")[2];
        return Long.parseLong(id);
    }
}
