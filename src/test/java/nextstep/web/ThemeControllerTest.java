package nextstep.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import nextstep.model.Theme;
import nextstep.repository.ThemeRepository;
import nextstep.repository.WebThemeRepository;
import nextstep.web.dto.ThemeRequest;
import nextstep.web.dto.ThemeResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ThemeControllerTest extends AbstractControllerTest {

    @Autowired
    private ThemeRepository themeRepository;

    @DisplayName("테마를 생성한다.")
    @Test
    void createTheme() {
        long id = 테마를_생성한다("name", "desc", 10000);

        assertThat(themeRepository.findAll())
                .extracting(Theme::getId, Theme::getName, Theme::getDesc, Theme::getPrice)
                .hasSize(1)
                .contains(tuple(id, "name", "desc", 10000));
    }

    @DisplayName("테마 목록을 조회한다.")
    @Test
    void getTheme() {
        long id1 = 테마를_생성한다("name1", "desc1", 10000);
        long id2 = 테마를_생성한다("name2", "desc2", 20000);

        List<ThemeResponse> actual = 테마를_조회한다();

        assertThat(actual)
                .extracting(ThemeResponse::getId, ThemeResponse::getName, ThemeResponse::getDesc,
                        ThemeResponse::getPrice)
                .hasSize(2)
                .contains(
                        tuple(id1, "name1", "desc1", 10000),
                        tuple(id2, "name2", "desc2", 20000)
                );
    }

    @DisplayName("테마를 삭제한다.")
    @Test
    void deleteTheme() {
        long id = 테마를_생성한다("name", "desc", 10000);

        테마를_삭제한다(id);

        List<ThemeResponse> themes = 테마를_조회한다();
        Assertions.assertThat(themes).isEmpty();
    }

    @ParameterizedTest
    @MethodSource
    void createByInvalidFormatRequest(String name, String desc, int price) {
        ThemeRequest request = new ThemeRequest(name, desc, price);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private static List<Arguments> createByInvalidFormatRequest() {
        return List.of(
                Arguments.of("n".repeat(256), "desc", 10_000),
                Arguments.of("name", "d".repeat(256), 10_000),
                Arguments.of("name", "desc", -1),
                Arguments.of("name", "desc", 100_001)
        );
    }
}
