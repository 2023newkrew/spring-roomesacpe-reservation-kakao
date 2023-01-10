package web.presentation;

import java.net.URI;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.domain.Reservation;
import web.domain.Theme;
import web.dto.request.ReservationRequestDTO;
import web.dto.response.ReservationResponseDTO;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private Long reservationIdIndex = 0L;
    private final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    @PostMapping("")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = reservationRequestDTO.toEntity(++reservationIdIndex, theme);
        reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservationIdIndex)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> retrieveReservation(@PathVariable Long id) {
        final ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO(1L, "2022-08-11", "13:00",
                "name",
                "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);

        return ResponseEntity.ok()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(reservationResponseDTO);
    }
}
