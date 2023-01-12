package nextstep.main.java.nextstep.mvc;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchThemeException;
import nextstep.main.java.nextstep.mvc.domain.theme.response.ThemeFindResponse;
import nextstep.main.java.nextstep.mvc.service.reservation.ReservationService;
import nextstep.main.java.nextstep.mvc.service.theme.ThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ThemeIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ThemeService themeService;
    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    @DisplayName("테마 생성")
    class CreateTheme {
        @Test
        @DisplayName("나홀로집에 테마를 생성한다.")
        void createSuccess() {
            String path = "/themes";

            given().
                contentType(ContentType.JSON).
                body("{"
                        + "\"name\": \"나홀로집에\","
                        + "\"desc\": \"테마의 설명을 10자 이상으로 입력\","
                        + "\"price\": 50000"
                        + "}"
                ).

            when().
                post(path).

            then().
                assertThat().
                    statusCode(HttpStatus.CREATED.value()).
                and().
                    header("Location", path + "/1");
        }

        @Test
        @DisplayName("잘못된 정보로 테마를 생성할 경우 오류메시지와 원인을 내어준다.")
        void createWithBadRequest() {
            String path = "/themes";

            given().
                contentType(ContentType.JSON).
                body("{"
                        + "\"name\": \"\","
                        + "\"desc\": \"짧은 테마 설명\","
                        + "\"price\": 900"
                        + "}"
                ).

            when().
                post(path).

            then().
                assertThat().
                    statusCode(HttpStatus.BAD_REQUEST.value()).
                and().
                    body("size()", is(3));
        }
    }

    @Nested
    @DisplayName("테마 조회")
    class FindTheme {
        @Test
        @DisplayName("100번 테마 정보를 조회한다.")
        void findSuccess() {
            String path = "/themes/100";

            given().

            when().
                get(path).

            then().
                assertThat().
                    body("id", equalTo(100));
        }

        @Test
        @DisplayName("존재하지 않는 테마를 조회해 오류메시지가 발생한다.")
        void findNotExists() {
            String path = "/themes/99";

            given().

            when().
                get(path).

            then().
                assertThat().
                    body("errorCode", equalTo(404)).
                    body("message", equalTo("존재하지 않는 테마입니다."));
        }

        @Test
        @DisplayName("모든 테마 정보를 조회한다.")
        void findAll() {
            String path = "/themes";

            given().

            when().
                get(path).

            then().
                assertThat().
                    statusCode(HttpStatus.OK.value()).
                and().
                    body("size()", is(2));
        }
    }

    @Nested
    @DisplayName("테마 삭제")
    class DeleteTheme {
        @Test
        @DisplayName("예약이 없는 테마를 삭제한다.")
        void deleteSuccess() {
            assertThatCode(() -> themeService.findById(101L)).doesNotThrowAnyException();

            String path = "/themes/101";

            given().

                    when().
                    delete(path).

                    then().
                    assertThat().
                    statusCode(HttpStatus.NO_CONTENT.value());

            assertThatThrownBy(() -> themeService.findById(101L)).isInstanceOf(NoSuchThemeException.class);
        }

        @Test
        @DisplayName("존재하지 않는 테마를 삭제하려해 오류메시지가 발생한다.")
        void deleteNotExists() {
            String path = "/themes/99";

            given().

            when().
                delete(path).

            then().
                assertThat().
                    body("errorCode", equalTo(404)).
                    body("message", equalTo("존재하지 않는 테마입니다."));
        }

        @Test
        @DisplayName("예약이 존재하는 테마를 삭제하려해 오류메시지가 발생한다.")
        void deleteAlreadyReserved() {
            String path = "/themes/100";

            given().

            when().
                delete(path).

            then().
                assertThat().
                    body("errorCode", equalTo(409)).
                    body("message", equalTo("예약이 존재하는 테마입니다. (삭제 불가)"));
        }
    }

    @Nested
    @DisplayName("테마 수정")
    class UpdateTheme {
        @Test
        @DisplayName("테마 정보를 수정한다.")
        void createSuccess() {
            String path = "/themes/100";
            Long id = 100L;

            given().
                contentType(ContentType.JSON).
                body("{"
                        + "\"name\": \"수정된 테마명\","
                        + "\"desc\": \"테마의 설명을 10자 이상으로 입력\","
                        + "\"price\": 10000"
                        + "}"
                ).

            when().
                put(path).

            then().
                assertThat().
                    statusCode(HttpStatus.NO_CONTENT.value());

            ThemeFindResponse updatedTheme = themeService.findById(id);

            assertThat(updatedTheme.getName()).isEqualTo("수정된 테마명");
            assertThat(updatedTheme.getDesc()).isEqualTo("테마의 설명을 10자 이상으로 입력");
            assertThat(updatedTheme.getPrice()).isEqualTo(10000);
        }
    }
}
