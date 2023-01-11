package roomescape.controller;

import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;


@DisplayName("웹 요청 / 응답 처리로 입출력 추가")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    private static final LocalDate DATE_DATA1 = LocalDate.parse("2022-08-01");
    private static final LocalDate DATE_DATA2 = LocalDate.parse("2022-08-02");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final String THEME_NAME_DATA = "워너고홈";
    private static final String THEME_DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int THEME_PRICE_DATA = 29000;

    private static final String SECOND_STRING = ":00";

    private static final Theme THEME_DATA = new Theme(THEME_NAME_DATA, THEME_DESC_DATA, THEME_PRICE_DATA);

    private static final String RESERVATIONS_PATH = "/reservations";
    private static final String FIRST_RESERVATION_PATH = RESERVATIONS_PATH + "/1";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS RESERVATION";
    private static final String CREATE_TABLE =
            "CREATE TABLE reservation "
                    + "(id bigint not null auto_increment,"
                    + " date date,"
                    + " time time,"
                    + " name varchar(20),"
                    + " theme_name varchar(20),"
                    + " theme_desc varchar(20),"
                    + " theme_price int,"
                    + " primary key (id));";
    private static final String ADD_SQL =
            "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) "
                    + "VALUES (?, ?, ?, ?, ?, ?);";

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        jdbcTemplate.execute(DROP_TABLE);
        jdbcTemplate.execute(CREATE_TABLE);

        List<Object[]> split = List.<Object[]>of(
                new Object[]{DATE_DATA1, TIME_DATA, NAME_DATA,
                        THEME_NAME_DATA, THEME_DESC_DATA, THEME_PRICE_DATA});

        jdbcTemplate.batchUpdate(ADD_SQL, split);
    }

    @DisplayName("예약 생성")
    @Test
    void createReservation() {
        Reservation reservation = new Reservation(DATE_DATA2, TIME_DATA, NAME_DATA, THEME_DATA);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("예약 조회")
    @Test
    void showReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(FIRST_RESERVATION_PATH)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", is(NAME_DATA))
                .body("date", is(DATE_DATA1.toString()))
                .body("time", is(TIME_DATA + SECOND_STRING));
    }

    @DisplayName("예약 취소")
    @Test
    void deleteReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(FIRST_RESERVATION_PATH)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
