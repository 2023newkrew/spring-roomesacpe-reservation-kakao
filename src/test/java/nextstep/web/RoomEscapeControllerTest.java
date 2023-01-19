package nextstep.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.exception.ThemeNotFoundException;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.repository.ReservationRepository;
import nextstep.web.dto.ReservationRequest;
import nextstep.web.dto.ReservationResponse;
import nextstep.web.dto.ThemeRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class RoomEscapeControllerTest extends AbstractControllerTest {

    @Autowired
    private ReservationRepository repository;

    @DisplayName("예약을 생성한다")
    @Test
    void createReservation() {
        // arrange
        long themeId = 테마를_생성한다("베루스홈", "베루스의 집", 50_000);

        String name = "예약_이름";
        LocalDate date = LocalDate.of(2022, 12, 14);
        LocalTime time = LocalTime.of(15, 5);

        // act
        Long id = 예약을_생성한다(themeId, name, date, time);

        // assert
        Reservation reservation = repository.findById(id).orElseThrow();
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getTheme().getId()).isEqualTo(themeId);
    }

    @DisplayName("예약을 조회한다")
    @Test
    void getReservation() {
        // arrange
        long themeId = 테마를_생성한다("베루스홈", "베루스의 집", 50_000);

        String name = "예약_이름";
        LocalDate date = LocalDate.of(2022, 4, 3);
        LocalTime time = LocalTime.of(12, 15);
        Long id = 예약을_생성한다(themeId, name, date, time);

        // act
        ReservationResponse reservation = 예약을_조회한다(id);

        // assert
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getThemeName()).isEqualTo("베루스홈");
        assertThat(reservation.getThemeDesc()).isEqualTo("베루스의 집");
        assertThat(reservation.getThemePrice()).isEqualTo(50_000);
    }

    @DisplayName("에약을 삭제한다")
    @Test
    void deleteReservation() {
        long themeId = 테마를_생성한다("베루스홈", "베루스의 집", 50_000);
        Long id = 예약을_생성한다(themeId, "취소될 예약", LocalDate.of(2023, 5, 29), LocalTime.of(8, 30));

        예약을_삭제한다(id);

        assertThat(repository.findById(id)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource
    void createReservationByInvalidFormatRequest(String name) {
        long themeId = 테마를_생성한다("베루스홈", "베루스의 집", 50_000);

        ReservationRequest request = new ReservationRequest(name, LocalDate.now(), LocalTime.now(), themeId);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private static List<Arguments> createReservationByInvalidFormatRequest() {
        return List.of(
                Arguments.of("n".repeat(256)),
                Arguments.of("  "),
                Arguments.of("\t\n")
        );
    }

    @DisplayName("존재하지 않는 예약을 조회할 경우 예외가 발생한다")
    @Test
    void getNotFoundReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 생성시 같은 날짜와 시간의 예약이 존재할 경우 예외가 발생한다")
    @Test
    void createDuplicateReservation() {
        long themeId = 테마를_생성한다("베루스홈", "베루스의 집", 50_000);
        LocalDate date = LocalDate.of(2023, 1, 23);
        LocalTime time = LocalTime.of(13, 0);
        예약을_생성한다(themeId, "name", date, time);

        ReservationRequest request = new ReservationRequest("name", date, time, themeId);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("존재하지 않는 테마로 예약을 생성할 수 없다.")
    @Test
    void createReservationByNotExistTheme() {
        ReservationRequest request = new ReservationRequest("name", LocalDate.now(), LocalTime.now(), 1L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
