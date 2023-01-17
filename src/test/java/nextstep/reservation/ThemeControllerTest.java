package nextstep.reservation;

import io.restassured.RestAssured;
import nextstep.reservation.dto.ThemeRequest;
import nextstep.reservation.dto.ThemeResponse;
import nextstep.reservation.service.ThemeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        themeService.clear();
    }

    @Test
    @DisplayName("테마 생성 성공시 201 반환한다.")
    void create() {
        //given
        RestAssured.port = port;
        ThemeRequest themeRequest = new ThemeRequest("호러", "매우 무서운", 24000);

        //when
        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("전체 테마 조회시 body에 JSON 배열 형태로 반환된다.")
    void findAll() {
        //given
        RestAssured.port = port;

        ThemeRequest themeRequest = new ThemeRequest("호러", "매우 무서운", 24000);
        themeService.registerTheme(themeRequest);

        ThemeRequest themeRequest2 = new ThemeRequest("ad", "cd", 2000);
        themeService.registerTheme(themeRequest2);

        //when
        //then
        List<ThemeResponse> themeList = RestAssured.given().log().all()
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().response().jsonPath()
                .getList("", ThemeResponse.class);

        Assertions.assertThat(themeTestEquals(themeRequest, themeList.get(0))).isTrue();
        Assertions.assertThat(themeTestEquals(themeRequest2, themeList.get(1))).isTrue();
    }

    @Test
    @DisplayName("테마 id로 삭제시 204 반환된다.")
    void deleteById() {
        //given
        RestAssured.port = port;

        ThemeRequest themeRequest = new ThemeRequest("호러", "매우 무서운", 24000);
        ThemeResponse created = themeService.registerTheme(themeRequest);

        //when
        //then
        RestAssured.given().log().all()
                .when().delete("/themes/" + created.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private boolean themeTestEquals(ThemeRequest a, ThemeResponse b) { //id를 제외한 Content 비교
        return a.getName().equals(b.getName()) &&
                a.getDesc().equals(b.getDesc()) &&
                a.getPrice().equals(b.getPrice());
    }
}
