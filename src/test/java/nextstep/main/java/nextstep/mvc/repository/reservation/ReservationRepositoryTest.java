package nextstep.main.java.nextstep.mvc.repository.reservation;

import nextstep.main.java.nextstep.global.exception.exception.NotSupportedOperationException;
import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.repository.CrudRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.Filter;
import static org.springframework.context.annotation.FilterType.ANNOTATION;

@JdbcTest(includeFilters = @Filter(type = ANNOTATION, classes = org.springframework.stereotype.Repository.class))
@Sql(scripts = "/sql/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("[예약 생성] 예약 생성 성공")
    void save() {
        Long id = 1L;
        ReservationCreateRequest request = getCreateRequest();
        Reservation reservation = createReservationFromIdAndRequest(id, request);
        Long newId = reservationRepository.save(request);

        assertThat(newId).isEqualTo(reservation.getId());
    }

    @Test
    @DisplayName("[예약 조회] 예약 조회 성공")
    void find() {
        Long id = 100L;

        assertThat(reservationRepository.findById(id)).isPresent();
    }

    @Test
    @DisplayName("[예약 조회] 존재하지 않는 예약 조회")
    void findNotExists() {
        Long id = 99L;

        assertThat(reservationRepository.findById(id)).isEmpty();
    }

    @Test
    @DisplayName("[예약 삭제] 예약 삭제 Delete 쿼리 정상작동 확인")
    void delete() {
        Long id = 100L;

        assertThat(reservationRepository.findById(id)).isPresent();
        assertThatCode(() -> reservationRepository.deleteById(id)).doesNotThrowAnyException();
        assertThat(reservationRepository.findById(id)).isEmpty();
    }


    @ParameterizedTest
    @DisplayName("[존재 확인] 특정 ID를 가진 예약 존재 여부 확인")
    @CsvSource(value = {"100:true", "99:false"}, delimiter = ':')
    void isExistsById(Long id, Boolean expected) {
        assertThat(reservationRepository.existsById(id)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("[존재 확인] 특정 테마 ID를 가진 예약 존재")
    @CsvSource(value = {"100:true", "99:false"}, delimiter = ':')
    void isExistsByThemeId(Long id, Boolean expected) {
        assertThat(reservationRepository.existsByThemeId(id)).isEqualTo(expected);
    }

    @Test
    @DisplayName("[존재 확인] 특정 일자를 가진 예약 존재")
    void isExistsByDateAndTimeTrue() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        LocalTime time = LocalTime.of(9, 30);
        assertThat(reservationRepository.existsByDateAndTime(date, time)).isEqualTo(true);
    }

    @Test
    @DisplayName("[존재 확인] 특정 일자를 가진 예약 미존재")
    void isExistsByDateAndTimeFalse() {
        LocalDate date = LocalDate.of(2024, 1, 2);
        LocalTime time = LocalTime.of(9, 30);
        assertThat(reservationRepository.existsByDateAndTime(date, time)).isEqualTo(false);
    }

    @Test
    @DisplayName("[미지원 기능] 예약 모두 조회는 지원하지 않음")
    void isNotSupportedFindAll() {
        assertThatThrownBy(() -> reservationRepository.findAll()).isInstanceOf(NotSupportedOperationException.class);
    }

    @Test
    @DisplayName("[미지원 기능] 예약 수정은 지원하지 않음")
    void isNotSupportedUpdate() {
        assertThatThrownBy(() -> reservationRepository.update(1L, getCreateRequest()))
                .isInstanceOf(NotSupportedOperationException.class);
    }

    private Reservation createReservationFromIdAndRequest(Long id, ReservationCreateRequest request) {
        return Reservation.builder()
                .id(id)
                .date(request.getDate())
                .time(request.getTime())
                .name(request.getName())
                .theme(new Theme(1L, request.getThemeName(), "description of theme", 50000))
                .build();
    }

    private ReservationCreateRequest getCreateRequest() {
        return ReservationCreateRequest.of(
                LocalDate.of(2023, 1, 19),
                LocalTime.of(3, 30),
                "name",
                "themeName");
    }
}
