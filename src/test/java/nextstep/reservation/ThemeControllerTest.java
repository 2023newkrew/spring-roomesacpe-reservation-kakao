package nextstep.reservation;

import io.restassured.RestAssured;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.service.ThemeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeControllerTest {
    @LocalServerPort
    int port;
    @Autowired
    private ThemeService themeService;
    private Theme theme;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        this.theme = new Theme(null, "호러", "매우 무서운", 24000);
    }

    @AfterEach
    void tearDown() {
        themeService.clear();
    }

    @Test
    @DisplayName("생성")
    void create() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(theme)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("전체 테마 조회")
    void findAll() {
        //given
        themeService.create(theme);
        Theme theme2 = new Theme(null, "ad", "cd", 2000);
        themeService.create(theme2);

        //when
        List<Theme> themeList = RestAssured.given().log().all()
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().response().jsonPath()
                .getList("", Theme.class);

        //then
        Assertions.assertThat(themeTestEquals(theme, themeList.get(0))).isTrue();
        Assertions.assertThat(themeTestEquals(theme2, themeList.get(1))).isTrue();
    }

    @Test
    @DisplayName("테마 id로 삭제")
    void deleteById() {
        //given
        Theme created = themeService.create(theme);

        //when
        RestAssured.given().log().all()
                .when().delete("/themes/" + created.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private boolean themeTestEquals(Theme a, Theme b) {
        return a.getName().equals(b.getName()) &&
                a.getDesc().equals(b.getDesc()) &&
                a.getPrice().equals(b.getPrice());
    }
}
