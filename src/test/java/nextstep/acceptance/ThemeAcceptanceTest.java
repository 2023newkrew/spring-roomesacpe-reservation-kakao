package nextstep.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.common.DatabaseExecutor;
import nextstep.dto.request.CreateThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static nextstep.domain.QuerySetting.Theme.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseExecutor databaseExecutor;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
        databaseExecutor.clearTable(TABLE_NAME);
    }

    @Test
    void 테마_생성에_성공한다() {
        // given
        CreateThemeRequest createThemeRequest = new CreateThemeRequest("테마 이름", "테마 설명", 22_000);

        // when
        ExtractableResponse<Response> response = createTheme(createThemeRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header(HttpHeaders.LOCATION)).isEqualTo("/themes" + 1);
    }

    @Nested
    @DisplayName("테마 생성 실패 케이스")
    class InvalidThemeCreateTest {

        @Test
        void 테마_이름을_기입하지_않은_경우_테마_생성에_실패한다() {
            // given
            CreateThemeRequest createThemeRequest = new CreateThemeRequest(null, "테마 설명", 22_000);

            // when
            ExtractableResponse<Response> response = createTheme(createThemeRequest);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 테마_설명을_기입하지_않은_경우_테마_생성에_실패한다() {
            // given
            CreateThemeRequest createThemeRequest = new CreateThemeRequest("테마 이름", null, 22_000);

            // when
            ExtractableResponse<Response> response = createTheme(createThemeRequest);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 테마_가격을_기입하지_않은_경우_테마_생성에_실패한다() {
            // given
            CreateThemeRequest createThemeRequest = new CreateThemeRequest("테마 이름", "테마 설명", null);

            // when
            ExtractableResponse<Response> response = createTheme(createThemeRequest);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Test
    void 테마_이름으로_테마를_조회한다() {
        // given
        CreateThemeRequest createThemeRequest = new CreateThemeRequest("베니스 상인 저택", "테마 설명", 25_000);
        createTheme(createThemeRequest);

        given()

        // when
        .when()
                .get("/themes?name=" + createThemeRequest.getName())

        // then
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .body("name", equalTo(createThemeRequest.getName()))
                .body("desc", equalTo(createThemeRequest.getDesc()))
                .body("price", equalTo(createThemeRequest.getPrice()));
    }

    @Test
    void 존재하지_않는_테마_이름으로_조회_시_조회에_실패한다() {
        // given
        String invalidThemeName = "거울의 방";

        given()

        // when
        .when()
                .get("/themes?name=" + invalidThemeName)

        // then
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private ExtractableResponse<Response> createTheme(CreateThemeRequest createThemeRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createThemeRequest)
        .when()
                .post("/themes")
        .then()
                .extract();
    }

}
