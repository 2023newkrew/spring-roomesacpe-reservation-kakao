package reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservation.domain.Reservation;
import reservation.domain.Theme;
import reservation.domain.dto.request.ReservationRequest;
import reservation.domain.dto.response.ReservationResponse;
import reservation.service.ReservationService;
import reservation.service.ThemeService;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ThemeService themeService;

    public ReservationController(ReservationService reservationService, ThemeService themeService) {
        this.reservationService = reservationService;
        this.themeService = themeService;
    }

    @PostMapping("")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                null,
                reservationRequest.getDate(),
                reservationRequest.getTime(),
                reservationRequest.getName(),
                reservationRequest.getThemeId()
        );
        Long id = reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<?> getReservation(@PathVariable Long reservationId) {
        Reservation reservation = reservationService.getReservation(reservationId);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        Theme theme = themeService.getTheme(reservation.getThemeId());
        return ResponseEntity.ok().body(new ReservationResponse(reservation, theme));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
