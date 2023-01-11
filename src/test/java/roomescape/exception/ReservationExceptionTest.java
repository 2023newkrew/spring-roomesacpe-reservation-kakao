package roomescape.exception;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;

@DisplayName("예외 처리")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationExceptionTest {

    private static final String ID_TABLE = "id";
    private static final String DATE_TABLE = "date";
    private static final String TIME_TABLE = "time";
    private static final String NAME_TABLE = "name";
    private static final String THEME_NAME_TABLE = "theme_name";
    private static final String THEME_DESC_TABLE = "theme_desc";
    private static final String THEME_PRICE_TABLE = "theme_price";

    private static final LocalDate DATE_DATA = LocalDate.parse("2022-08-01");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final String THEME_NAME_DATA = "워너고홈";
    private static final String THEME_DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int THEME_PRICE_DATA = 29000;

    private static final Theme THEME_DATA = new Theme(
            THEME_NAME_DATA, THEME_DESC_DATA, THEME_PRICE_DATA);

    private static final String RESERVATIONS_PATH = "/reservations";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS reservation";
    private static final String CREATE_TABLE = String.format(
            "CREATE TABLE reservation "
                    + "(%s bigint not null auto_increment,"
                    + " %s date, %s time,"
                    + " %s varchar(20),"
                    + " %s varchar(20),"
                    + " %s varchar(20),"
                    + " %s int,"
                    + " primary key (%s)",
            ID_TABLE, DATE_TABLE, TIME_TABLE, NAME_TABLE,
            THEME_NAME_TABLE, THEME_DESC_TABLE, THEME_PRICE_TABLE,
            ID_TABLE);
    private static final String ADD_SQL =String.format(
            "INSERT INTO reservation (%s, %s, %s, %s, %s, %s) "
                    + "VALUES (?, ?, ?, ?, ?, ?)",
            NAME_TABLE, DATE_TABLE, TIME_TABLE,
            THEME_NAME_TABLE, THEME_DESC_TABLE, THEME_PRICE_TABLE);

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
                new Object[]{DATE_DATA, TIME_DATA, NAME_DATA,
                        THEME_NAME_DATA, THEME_DESC_DATA, THEME_PRICE_DATA});

        jdbcTemplate.batchUpdate(ADD_SQL, split);
    }

    @DisplayName("content-type이 application/json이 아닌 경우 값을 받지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {MediaType.TEXT_PLAIN_VALUE, MediaType.TEXT_HTML_VALUE,
            MediaType.TEXT_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    void notJson(String contentType) {
        RestAssured.given().log().all()
                .contentType(contentType).body("").when()
                .post("/reservations").then().log().all()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    @DisplayName("예약 생성) 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.")
    @Test
    void failToCreateReservationAlreadyExist() {
        Reservation reservation = new Reservation(DATE_DATA, TIME_DATA, NAME_DATA, THEME_DATA);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 생성) 값이 포함되지 않았을 경우 예약 생설 불가.")
    @ParameterizedTest
    @ValueSource(strings = {"{\"name\":\"abc\",\"time\":\"13:00:00\"}",
            "{\"name\":\"abc\",\"date\":\"2022-08-12\"}",
            "{\"date\":\"2022-08-12\",\"time\":\"13:00:00\"}",})
    void notContainRequiredField(String body) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 생성) 값의 포맷이 맞지 않을 경우 생성 불가")
    @ParameterizedTest
    @ValueSource(strings = {"{\"name\":\"abc\",\"date\":\"2022-08\",\"time\":\"13:00:00\"}",
            "{\"name\":\"abc\",\"date\":\"2022-08-09\",\"time\":\"13\"}",
            "{\"name\":\"abc\",\"date\":\"test\",\"time\":\"13:00:00\"}",
            "{\"name\":\"abc\",\"date\":\"2022-08-09\",\"time\":13}"})
    void isNotValidField(String body) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 조회) ID가 없는 경우 조회 불가 및 ID가 잘못된 경우 (float, string)")
    @ParameterizedTest
    @ValueSource(strings = {RESERVATIONS_PATH + "/10", RESERVATIONS_PATH + "/1.1",
            RESERVATIONS_PATH + "/test"})
    void notExistID(String path) {
        RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 삭제) ID가 없는 경우 조회 불가 및 ID가 잘못된 경우 (float, string)")
    @ParameterizedTest
    @ValueSource(strings = {RESERVATIONS_PATH + "/10", RESERVATIONS_PATH + "/1.1",
            RESERVATIONS_PATH + "/test"})
    void failToDeleteWithInvalidId(String path) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
