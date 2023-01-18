package reservation.theme;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reservation.model.domain.Theme;
import reservation.model.dto.RequestTheme;
import reservation.respository.ReservationJdbcTemplateRepository;
import reservation.respository.ThemeJdbcTemplateRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {
    @LocalServerPort
    int port;

    private final ThemeJdbcTemplateRepository themeRepository;
    private final ReservationJdbcTemplateRepository reservationRepository;

    @Autowired
    public ControllerTest(ThemeJdbcTemplateRepository themeRepository, ReservationJdbcTemplateRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("POST /themes")
    @Test
    void postReservation() {
        Long lastId = returnLastId("post");

        RequestTheme requestTheme = new RequestTheme("name", "desc", 10000);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestTheme)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/" + (lastId + 1));
    }

    @DisplayName("GET /themes/{id}")
    @Test
    void getReservation() {
        given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("DELETE /themes/{id}")
    @Test
    void deleteReservation() {
        Long lastId = returnLastId("delete");
        given().log().all()
                .accept(MediaType.TEXT_PLAIN_VALUE)
                .when().delete("/themes/" + lastId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .body(is(""));
    }

    private Long returnLastId(String flag){
        // 하나의 Theme를 넣어서 현재 마지막 id 값을 받아온다.
        Theme theme = new Theme(1L, "test_" + flag, "desc", 10000);
        return themeRepository.save(theme);
    }
}
