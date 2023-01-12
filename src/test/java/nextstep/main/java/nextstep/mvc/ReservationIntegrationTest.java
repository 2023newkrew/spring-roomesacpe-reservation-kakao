package nextstep.main.java.nextstep.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.mvc.service.reservation.ReservationService;
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
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ReservationIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    @DisplayName("예약 생성")
    class CreateReservation {
        @Test
        @DisplayName("2023-01-19, 3:30분에 themeName 테마를 예약한다.")
        void createSuccess() {
            String path = "/reservations";

            given().
                    contentType(ContentType.JSON).
                    body("{"
                            + "\"date\": \"2023-01-19\","
                            + "\"time\": \"03:30:00\","
                            + "\"name\": \"name\","
                            + "\"themeName\": \"themeName\""
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
        @DisplayName("동일한 예약 생성 시도 시 오류코드와 메시지를 넘겨준다.")
        void createDuplicate() {
            String path = "/reservations";

            given().
                contentType(ContentType.JSON).
                body("{"
                        + "\"date\": \"2024-01-01\","
                        + "\"time\": \"09:30:00\","
                        + "\"name\": \"name\","
                        + "\"themeName\": \"themeName\""
                        + "}"
                ).

            when().
                post(path).

            then().
                assertThat().
                    body("errorCode", equalTo(409)).
                    body("message", equalTo("날짜와 시간이 동일한 예약이 이미 존재합니다."));
        }

        @Test
        @DisplayName("잘못된 정보로 예약을 생성할 경우 오류메시지와 원인을 내어준다.")
        void createWithBadRequest() {
            String path = "/reservations";

            given().
                contentType(ContentType.JSON).
                body("{"
                        + "\"date\": \"2022-01-01\","
                        + "\"time\": \"09:30:00\","
                        + "\"name\": \"n\","
                        + "\"themeName\": \"\""
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
    @DisplayName("예약 조회")
    class FindReservation {
        @Test
        @DisplayName("100번 예약 정보를 조회한다.")
        void findSuccess() {
            String path = "/reservations/100";

            given().

            when().
                get(path).

            then().
                assertThat().
                    body("id", equalTo(100));
        }

        @Test
        @DisplayName("존재하지 않는 예약을 조회해 오류메시지가 발생한다.")
        void findNotExists() {
            String path = "/reservations/99";

            given().

            when().
                get(path).

            then().
                assertThat().
                    body("errorCode", equalTo(404)).
                    body("message", equalTo("존재하지 않는 예약입니다."));
        }
    }

    @Nested
    @DisplayName("예약 삭제")
    class DeleteReservation {
        @Test
        @DisplayName("100번 예약을 삭제한다.")
        void deleteSuccess() {
            assertThatCode(() -> reservationService.findById(100L)).doesNotThrowAnyException();

            String path = "/reservations/100";

            given().

            when().
                delete(path).

            then().
                assertThat().
                    statusCode(HttpStatus.NO_CONTENT.value());

            assertThatThrownBy(() -> reservationService.findById(100L)).isInstanceOf(NoSuchReservationException.class);
        }

        @Test
        @DisplayName("존재하지 않는 예약을 삭제하려해 오류메시지가 발생한다.")
        void deleteNotExists() {
            String path = "/reservations/99";

            given().

            when().
                delete(path).

            then().
                assertThat().
                    body("errorCode", equalTo(404)).
                    body("message", equalTo("존재하지 않는 예약입니다."));
        }
    }
}
