package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;

    @DisplayName("Reservation 저장 후 조회 가능함을 확인")
    @Test
    @Transactional
    public void createAndFindReservationTest() {
        //given
        ReservationRequestDto reservationRequestDto =
                new ReservationRequestDto(LocalDate.now(), LocalTime.now(), "Tester");
        //when
        Long reservationId = reservationService.createReservation(reservationRequestDto);
        //then
        Assertions.assertThatNoException().isThrownBy(() -> reservationService.findReservation(reservationId));
    }

    @DisplayName("중복 Reservation 저장 시 예외 확인")
    @Test
    @Transactional
    public void createDuplicateReservationTest() {
        //given
        ReservationRequestDto reservationRequestDto =
                new ReservationRequestDto(
                        LocalDate.of(2023,1,11),
                        LocalTime.of(11,11,11),
                        "Tester"
                );
        //when
        reservationService.createReservation(reservationRequestDto);
        //then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> reservationService.createReservation(reservationRequestDto));
    }
}
