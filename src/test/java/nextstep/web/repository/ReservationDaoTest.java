package nextstep.web.repository;

import nextstep.domain.Reservation;
import nextstep.web.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
class ReservationDaoTest {

    @Autowired
    ReservationDao reservationDao;

    Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = Reservation.builder()
                .date(LocalDate.of(2023, 1, 10))
                .time(LocalTime.MIDNIGHT)
                .name("베인")
                .themeId(1L)
                .build();
    }

    @Test
    @Transactional
    void 예약을_저장후_조회할_수_있다() {
        Long createdId = reservationDao.save(reservation);

        Assertions.assertThat(reservationDao.findById(createdId)
                        .getId())
                .isEqualTo(createdId);
        Assertions.assertThat(reservationDao.findById(createdId)
                        .getName())
                .isEqualTo(reservation.getName());
    }

    @Test
    @Transactional
    void 예약후_취소할_수_있다() {
        Long createdId = reservationDao.save(reservation);
        reservationDao.deleteById(createdId);

        Assertions.assertThatThrownBy(() -> reservationDao.findById(createdId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @Transactional
    void 없는_예약을_취소하면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> reservationDao.deleteById(1L))
                .isInstanceOf(BusinessException.class);
    }
}
