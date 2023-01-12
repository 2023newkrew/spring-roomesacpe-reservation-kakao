package roomescape.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.dto.request.ReservationRequestDTO;
import roomescape.dto.response.ReservationResponseDTO;
import roomescape.exception.DuplicatedReservationException;
import roomescape.exception.NoSuchReservationException;
import roomescape.repository.ReservationRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final Theme defaultTheme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    private final ReservationRepository reservationRepository;

    @PostMapping("")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = reservationRequestDTO.toEntity(defaultTheme);

        this.reservationRepository.findByDateAndTime(reservation.getDate(), reservation.getTime())
                .ifPresent((e) -> {
                    throw new DuplicatedReservationException();
                });

        Long newReservationId = this.reservationRepository.save(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservationId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> retrieveReservation(@PathVariable Long id) {
        Reservation reservation = this.reservationRepository.findById(id)
                .orElseThrow(NoSuchReservationException::new);

        final ReservationResponseDTO reservationResponseDTO = ReservationResponseDTO.from(reservation);

        return ResponseEntity.ok()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(reservationResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean deleted = this.reservationRepository.deleteById(id);

        if (!deleted) {
            throw new NoSuchReservationException();
        }

        return ResponseEntity.noContent().build();
    }
}
