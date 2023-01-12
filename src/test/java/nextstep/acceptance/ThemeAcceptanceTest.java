package nextstep.acceptance;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.common.DatabaseExecutor;
import nextstep.common.fixture.ThemeProvider;
import nextstep.dto.request.CreateReservationRequest;
import nextstep.dto.request.CreateOrUpdateThemeRequest;
import nextstep.dto.response.FindThemeResponse;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static nextstep.common.fixture.ReservationProvider.예약_생성을_요청한다;
import static org.assertj.core.api.Assertions.assertThat;

import static nextstep.common.fixture.ThemeProvider.테마_생성을_요청한다;
import static org.hamcrest.CoreMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseExecutor databaseExecutor;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
        databaseExecutor.clearAll();
    }

    @Test
    void 테마_생성에_성공한다() {
        // given
        CreateOrUpdateThemeRequest createThemeRequest = new CreateOrUpdateThemeRequest("테마 이름", "테마 설명", 22_000);

        // when
        ExtractableResponse<Response> response = 테마_생성을_요청한다(createThemeRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header(HttpHeaders.LOCATION)).isEqualTo("/themes/" + 1);
    }

    @Nested
    @DisplayName("테마 생성 실패 케이스")
    class InvalidThemeCreateTest {

        @Test
        void 테마_이름을_기입하지_않은_경우_테마_생성에_실패한다() {
            // given
            CreateOrUpdateThemeRequest createThemeRequest = new CreateOrUpdateThemeRequest(null, "테마 설명", 22_000);

            // when
            ExtractableResponse<Response> response = 테마_생성을_요청한다(createThemeRequest);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 테마_설명을_기입하지_않은_경우_테마_생성에_실패한다() {
            // given
            CreateOrUpdateThemeRequest createThemeRequest = new CreateOrUpdateThemeRequest("테마 이름", null, 22_000);

            // when
            ExtractableResponse<Response> response = 테마_생성을_요청한다(createThemeRequest);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 테마_가격을_기입하지_않은_경우_테마_생성에_실패한다() {
            // given
            CreateOrUpdateThemeRequest createThemeRequest = new CreateOrUpdateThemeRequest("테마 이름", "테마 설명", null);

            // when
            ExtractableResponse<Response> response = 테마_생성을_요청한다(createThemeRequest);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Test
    void 테마_이름으로_테마를_조회한다() {
        // given
        CreateOrUpdateThemeRequest createThemeRequest = new CreateOrUpdateThemeRequest("베니스 상인 저택", "테마 설명", 25_000);
        테마_생성을_요청한다(createThemeRequest);

        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)

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

    @Nested
    @DisplayName("테마 목록 조회 성공 케이스")
    class ThemeFindTest{

        private List<CreateOrUpdateThemeRequest> createThemeRequests = Stream.of("혜화 잡화점", "거울의 방", "베니스 상인의 저택", "워너고홈", "명당", "스위트홈", "오즈의 마법사", "테마1", "테마2", "테마3", "테마4")
                .map(themeName -> new CreateOrUpdateThemeRequest(themeName, "테마 설명", 22_000))
                .collect(Collectors.toList());

        @Test
        void 테마_목록을_조회한다() {
            // given
            int page = 0, size = 10;
            createThemeRequests.forEach(ThemeProvider::테마_생성을_요청한다);

            List<FindThemeResponse> response = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)

            // when
            .when()
                    .get("/themes?page={page}&size={size}", page, size)

            // then
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(new TypeRef<List<FindThemeResponse>>() {});

            assertThat(response).hasSize(size);
            response.forEach(findThemeResponse -> assertThat(findThemeResponse).isNotNull());
        }

        @Test
        void 다음_테마_목록을_조회한다() {
            // given
            int page = 1, size = 10;
            int offset = page * size;
            createThemeRequests.forEach(ThemeProvider::테마_생성을_요청한다);

            List<FindThemeResponse> response = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)

            // when
            .when()
                    .get("/themes?page={page}&size={size}", page, size)
            // then
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(new TypeRef<List<FindThemeResponse>>() {});

            assertThat(response).hasSize(createThemeRequests.subList(offset, Math.min(offset + size, createThemeRequests.size())).size());
            response.forEach(findThemeResponse -> assertThat(findThemeResponse).isNotNull());
        }


    }


    @Nested
    @DisplayName("테마 목록 조회 실패 케이스")
    class InvalidThemeFindTest {

        @Test
        void 조회하려는_범위가_기입되지_않은_경우_조회에_실패한다() {
            // given
            int page = 0;

            given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)

            // when
            .when()
                    .get("/themes?page={page}&size=", page)

            // then
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 조회하려는_범위가_숫자가_아닌_경우_조회에_실패한다() {
            // given
            int page = 1;
            String size = "abc";

            given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)

            // when
            .when()
                    .get("/themes?page={page}&size={size}", page, size)

            // then
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 조회하려는_개수가_요청_당_최대_조회_개수를_넘는_경우_조회에_실패한다() {
            // given
            int page = 2, size = 20000;

            given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)

            // when
            .when()
                    .get("/themes?page={page}&size={size}", page, size)

            // then
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Test
    void 테마_아이디로_테마를_삭제한다() {
        // given
        ExtractableResponse<Response> response = 테마_생성을_요청한다(new CreateOrUpdateThemeRequest("베니스 상인 저택", "테마 설명", 25_000));

        given()

        // when
        .when()
                .delete(response.header(HttpHeaders.LOCATION))

        // then
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 삭제하려는_테마에_예약이_존재하면_삭제할_수_없다() {
        // given
        String themeName = "베니스 상인 저택";
        ExtractableResponse<Response> response = 테마_생성을_요청한다(new CreateOrUpdateThemeRequest(themeName, "테마 설명", 25_000));
        예약_생성을_요청한다(new CreateReservationRequest("2023-01-12", "13:00", "eddie", themeName));

        given()

        // when
        .when()
                .delete(response.header(HttpHeaders.LOCATION))

        // then
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 테마_정보를_수정한다() {
        // given
        CreateOrUpdateThemeRequest createThemeRequest = new CreateOrUpdateThemeRequest("테마 제목", "테마 설명", 33_000);
        ExtractableResponse<Response> response = 테마_생성을_요청한다(createThemeRequest);
        CreateOrUpdateThemeRequest updateThemeRequest = new CreateOrUpdateThemeRequest("새로운 테마 제목", "새로운 테마 설명", createThemeRequest.getPrice() + 10000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateThemeRequest)

        // when
        .when()
                .put(response.header(HttpHeaders.LOCATION))

        // then
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 수정하려는_테마_정보를_모두_기입하지_않으면_수정에_실패() {
        // given
        ExtractableResponse<Response> response = 테마_생성을_요청한다(new CreateOrUpdateThemeRequest("테마 제목", "테마 설명", 33_000));
        CreateOrUpdateThemeRequest updateThemeRequest = new CreateOrUpdateThemeRequest(null, "새로운 테마 설명", 10_000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateThemeRequest)

        // when
        .when()
                .put(response.header(HttpHeaders.LOCATION))

        // then
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 수정하려는_테마에_예약이_존재하면_수정할_수_없다() {
        // given
        String themeName = "테마 제목";
        ExtractableResponse<Response> response = 테마_생성을_요청한다(new CreateOrUpdateThemeRequest(themeName, "테마 설명", 33_000));
        예약_생성을_요청한다(new CreateReservationRequest("2023-01-12", "15:00", "eddie", themeName));

        CreateOrUpdateThemeRequest updateThemeRequest = new CreateOrUpdateThemeRequest("새로운 테마 제목", "새로운 테마 설명", 30_000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateThemeRequest)

        // when
        .when()
                .put(response.header(HttpHeaders.LOCATION))

        // then
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
