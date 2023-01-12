package roomservice.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomservice.domain.TimeTable;
import roomservice.domain.dto.ReservationDto;
import roomservice.exceptions.exception.InvalidReservationTimeException;
import roomservice.exceptions.exception.InvalidThemeException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;

    @Test
    void createTest(){
        assertThat(reservationService.createReservation(new ReservationDto(1L,
                LocalDate.now(),
                TimeTable.A.getTime(),
                "Kim"))).isEqualTo(3L);
    }
    @Test
    void notCreateIfNotValidThemeId(){
        assertThatThrownBy(()->{reservationService.createReservation(new ReservationDto(
                2L,
                LocalDate.now(),
                TimeTable.A.getTime(),
                "Kim"));
        }).isInstanceOf(InvalidThemeException.class);
    }

    @Test
    void notCreateIfTimeIsNotValid(){
        assertThatThrownBy(()->{reservationService.createReservation(new ReservationDto(
                1L,
                LocalDate.now(),
                LocalTime.of(13,22),
                "Kim"));
        }).isInstanceOf(InvalidReservationTimeException.class);
    }
}
