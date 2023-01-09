package nextstep.web.service;

import lombok.RequiredArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.dto.ReservationRequestDto;
import nextstep.web.dto.ReservationResponseDto;
import nextstep.web.repository.ReservationRepository;
import org.springframework.stereotype.Service;

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
