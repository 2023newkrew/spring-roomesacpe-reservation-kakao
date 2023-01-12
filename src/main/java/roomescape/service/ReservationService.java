package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.dto.request.ReservationRequestDTO;
import roomescape.dto.response.ReservationResponseDTO;
import roomescape.exception.DuplicatedReservationException;
import roomescape.exception.NoSuchReservationException;
import roomescape.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final Theme defaultTheme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    public Long save(final ReservationRequestDTO reservationRequestDTO) {
        final Reservation reservation = reservationRequestDTO.toEntity(defaultTheme);

        this.reservationRepository.findByDateAndTime(reservation.getDate(), reservation.getTime())
                .ifPresent((e) -> {
                    throw new DuplicatedReservationException();
                });

        return this.reservationRepository.save(reservation);
    }

    public ReservationResponseDTO findById(final Long id) {
        Reservation reservation = this.reservationRepository.findById(id)
                .orElseThrow(NoSuchReservationException::new);

        return ReservationResponseDTO.from(reservation);
    }

    public void deleteById(Long id) {
        boolean deleted = this.reservationRepository.deleteById(id);

        if (!deleted) {
            throw new NoSuchReservationException();
        }
    }
}
