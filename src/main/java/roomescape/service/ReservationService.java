package roomescape.service;

import lombok.RequiredArgsConstructor;
import nextstep.Reservation;
import nextstep.Theme;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public int createReservation(ReservationRequestDto requestDto) {
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        Reservation reservation = new Reservation(
                requestDto.getDate(),
                requestDto.getTime(),
                requestDto.getName(),
                theme
        );

        reservationRepository.save(reservation);

        return 1;
    }

    public ReservationResponseDto findReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);

        return ReservationResponseDto.of(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
