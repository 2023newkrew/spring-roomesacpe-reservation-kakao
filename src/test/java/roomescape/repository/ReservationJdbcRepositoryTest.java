package roomescape.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.model.Reservation;
import roomescape.model.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@SpringBootTest
public class ReservationJdbcRepositoryTest {
    @Autowired
    private ReservationJdbcRepository reservationRepository;
    @Autowired
    private ThemeJdbcRepository themeRepository;

    private Theme theme;

    @BeforeEach
    @Transactional
    void setUp() {
        long themeId = themeRepository.save(new Theme(null, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));
        theme = themeRepository.findOneById(themeId).get();
    }

    @DisplayName("Reservation 저장 후 데이터베이스에 존재함을 확인")
    @Test
    @Transactional
    public void saveAndFindByIdTest() {
        //given
        Reservation reservation = new Reservation(null, LocalDate.now(), LocalTime.now(), "Test", theme);
        //when
        long reservationId = reservationRepository.save(reservation);
        Optional<Reservation> optionalReservation = reservationRepository.findOneById(reservationId);
        //then
        Assertions.assertThat(optionalReservation).isNotEmpty();
    }

    @DisplayName("Reservation 저장, 삭제 후 데이터베이스에 존재하지 않음을 확인")
    @Test
    @Transactional
    public void deleteAndFindByIdTest() {
        //given
        Reservation reservation = new Reservation(null, LocalDate.now(), LocalTime.now(), "Test", theme);
        //when
        long reservationId = reservationRepository.save(reservation);
        reservationRepository.delete(reservationId);
        Optional<Reservation> optionalReservation = reservationRepository.findOneById(reservationId);
        //then
        Assertions.assertThat(optionalReservation).isEmpty();
    }

    @DisplayName("특정 날짜, 시간의 Reservation 검출")
    @Test
    @Transactional
    public void hasOneByDateAndTimeTest() {
        //given
        LocalDate date = LocalDate.of(2023, 1,10);
        LocalTime time = LocalTime.of(11,11,11);
        Reservation reservation = new Reservation(null, date, time, "Test", theme);
        //when
        long reservationId = reservationRepository.save(reservation);
        //then
        Assertions.assertThat(reservationRepository.hasOneByDateAndTime(date, time)).isTrue();
    }
}
