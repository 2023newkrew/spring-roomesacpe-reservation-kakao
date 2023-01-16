package nextstep.web;

import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import nextstep.web.dto.ReservationRequest;
import nextstep.web.dto.ReservationResponse;
import nextstep.web.dto.ThemeRequest;
import nextstep.web.dto.ThemeResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        themeRepository.deleteAll();
    }

    protected Long 예약을_생성한다(long themeId, String name, LocalDate date, LocalTime time) {
        ReservationRequest request = new ReservationRequest(name, date, time, themeId);

        String id = RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).body(request).when()
                .post("/reservations").then().statusCode(HttpStatus.CREATED.value())
                .header(HttpHeaders.LOCATION, notNullValue()).extract().header(HttpHeaders.LOCATION).split("/")[2];

        return Long.parseLong(id);
    }

    protected ReservationResponse 예약을_조회한다(Long id) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .get("/reservations/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ReservationResponse.class);
    }

    protected void 예약을_삭제한다(Long id) {
        RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).when()
                .delete("/reservations/" + id).then().statusCode(HttpStatus.NO_CONTENT.value()).extract();
    }

    protected long 테마를_생성한다(String name, String desc, int price) {
        ThemeRequest request = new ThemeRequest(name, desc, price);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/themes")
                .then().log().all()
                .extract();

        String id = response.header(HttpHeaders.LOCATION).split("/")[2];
        return Long.parseLong(id);
    }

    protected List<ThemeResponse> 테마를_조회한다() {
        return RestAssured.given().log().all()
                .when().log().all()
                .get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body().jsonPath().getList(".", ThemeResponse.class);
    }

    protected void 테마를_삭제한다(long id) {
        RestAssured.given().log().all()
                .when().log().all()
                .delete("/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }
}
