package nextstep.service;

import nextstep.domain.reservation.repository.ReservationRepository;
import nextstep.error.ApplicationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReservationReadServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationReadService reservationReadService;

    @Test
    void 존재하지_않는_예약을_조회할_경우_예외가_발생한다() {
        // given
        Long invaildId = 500L;

        given(reservationRepository.findById(invaildId))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> reservationReadService.findReservationById(invaildId))
                .isInstanceOf(ApplicationException.class);
    }

}
