package nextstep.theme.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import nextstep.RoomEscapeWebApplication;
import nextstep.reservation.dto.ReservationDto;
import nextstep.theme.ThemeMock;
import nextstep.theme.dto.ThemeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RoomEscapeWebApplication.class)
@Transactional
class ThemeControllerTest {
    public static final String THEME_URL = "/themes";
    public static final Long NOT_EXIST_THEME_ID = Long.MAX_VALUE;
    public static final Object EMPTY_BODY = new ReservationDto();
    public static final String REQUEST_ACCEPT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;
    public static final String REQUEST_CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp(){
        RestAssured.port = port;
    }

    @Test
    @DisplayName("테마 생성 성공 시 상태코드는 CREATED이다.")
    void test1(){
        ThemeDto themeDto = ThemeMock.makeRandomThemeDto();
        getValidationResponse(themeDto, HttpMethod.POST, THEME_URL)
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("테마 생성 정보가 온전히 저장되어야 한다.")
    void test2(){
        ThemeDto themeDto = ThemeMock.makeRandomThemeDto();
        String location = sendRequest(themeDto, HttpMethod.POST, THEME_URL)
                .getHeader(HttpHeaders.LOCATION);

        getValidationResponse(themeDto, HttpMethod.GET, location)
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("name", equalTo(themeDto.getName()))
                .body("desc", equalTo(themeDto.getDesc()))
                .body("price", equalTo(themeDto.getPrice()));
    }

    @Test
    @DisplayName("존재하지 않은 테마 정보를 조회할 경우 상태 코드는 NOT_FOUND이다.")
    void test3(){
        getValidationResponse(EMPTY_BODY, HttpMethod.GET, THEME_URL + "/" + NOT_EXIST_THEME_ID)
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("존재하지 않은 테마 정보를 삭제 요청하는 경우 상태 코드는 NOT_FOUND이다.")
    void test4(){
        getValidationResponse(EMPTY_BODY, HttpMethod.DELETE, THEME_URL + "/" + NOT_EXIST_THEME_ID)
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("삭제한 예약은 조회할 수 없어야 한다.")
    void test5(){
        ThemeDto themeDto = ThemeMock.makeRandomThemeDto();

        String location = sendRequest(themeDto, HttpMethod.POST, THEME_URL)
                .getHeader(HttpHeaders.LOCATION);

        sendRequest(EMPTY_BODY, HttpMethod.DELETE, location);

        getValidationResponse(EMPTY_BODY, HttpMethod.GET, location)
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private static Response sendRequest(Object body, HttpMethod httpMethod, String url) {
        return RestAssured.given().log().all()
                .accept(REQUEST_ACCEPT_MEDIA_TYPE)
                .contentType(REQUEST_CONTENT_TYPE)
                .body(body)
                .when().request(httpMethod.toString(), url);
    }

    private static ValidatableResponse getValidationResponse(Object body, HttpMethod httpMethod, String url) {
        return sendRequest(body, httpMethod, url)
                .then().log().all();
    }
}
