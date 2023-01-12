package nextstep.web;

import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.service.ReservationService;
import nextstep.dto.ReservationRequest;
import nextstep.dto.ReservationResponse;
import nextstep.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ReservationController {
    private final ReservationService reservationService;
    private final ThemeService themeService;

    @Autowired
    public ReservationController(ReservationService reservationService, ThemeService themeService) {
        this.reservationService = reservationService;
        this.themeService = themeService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequest request) {
        themeService.getTheme(request.getThemeId());
        Reservation reservation = reservationService.createReservation(request);
        return ResponseEntity.created(URI.create("/reservations/"+ reservation.getId())).build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservation(id);
        Theme theme = themeService.getTheme(reservation.getThemeId());
        return ResponseEntity.ok(new ReservationResponse(reservation, theme));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
