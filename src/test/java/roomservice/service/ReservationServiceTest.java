package roomservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomservice.domain.TimeTable;
import roomservice.domain.dto.ReservationCreateDto;
import roomservice.exceptions.exception.InvalidReservationTimeException;
import roomservice.exceptions.exception.InvalidThemeIdException;

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
        Long givenId = reservationService.createReservation(new ReservationCreateDto(1L,
                LocalDate.now(),
                TimeTable.A.getTime(),
                "Kim"));
        assertThat(reservationService.findReservation(givenId).getName()).isEqualTo("Kim");
    }
    @Test
    void notCreateIfNotValidThemeId(){
        assertThatThrownBy(()->{reservationService.createReservation(new ReservationCreateDto(
                2L,
                LocalDate.now(),
                TimeTable.A.getTime(),
                "Kim"));
        }).isInstanceOf(InvalidThemeIdException.class);
    }

    @Test
    void notCreateIfTimeIsNotValid(){
        assertThatThrownBy(()->{reservationService.createReservation(new ReservationCreateDto(
                1L,
                LocalDate.now(),
                LocalTime.of(13,22),
                "Kim"));
        }).isInstanceOf(InvalidReservationTimeException.class);
    }
}
