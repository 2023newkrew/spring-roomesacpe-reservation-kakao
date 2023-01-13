package nextstep.reservation;

import io.restassured.RestAssured;
import nextstep.reservation.dto.ThemeRequest;
import nextstep.reservation.dto.ThemeResponse;
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
    private ThemeRequest themeRequest;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        this.themeRequest = new ThemeRequest("호러", "매우 무서운", 24000);
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
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("전체 테마 조회")
    void findAll() {
        //given
        themeService.registerTheme(themeRequest);
        ThemeRequest themeRequest2 = new ThemeRequest("ad", "cd", 2000);
        themeService.registerTheme(themeRequest2);

        //when
        List<ThemeResponse> themeList = RestAssured.given().log().all()
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().response().jsonPath()
                .getList("", ThemeResponse.class);

        //then
        Assertions.assertThat(themeTestEquals(themeRequest, themeList.get(0))).isTrue();
        Assertions.assertThat(themeTestEquals(themeRequest2, themeList.get(1))).isTrue();
    }

    @Test
    @DisplayName("테마 id로 삭제")
    void deleteById() {
        //given
        ThemeResponse created = themeService.registerTheme(themeRequest);

        //when
        RestAssured.given().log().all()
                .when().delete("/themes/" + created.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private boolean themeTestEquals(ThemeRequest a, ThemeResponse b) {
        return a.getName().equals(b.getName()) &&
                a.getDesc().equals(b.getDesc()) &&
                a.getPrice().equals(b.getPrice());
    }
}
