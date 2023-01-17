package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservation;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ThemeService themeService;

    @Autowired
    public ReservationController(ReservationService reservationService, ThemeService themeService) {
        this.reservationService = reservationService;
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity createReservation(@RequestBody CreateReservationRequest request) {
        Theme theme = themeService.findById(request.getThemeId());
        Long id = reservationService.createReservation(request.getDate(),
                request.getTime(), request.getName(), theme.getId());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public FindReservation findReservationById(@PathVariable Long id) {

        Reservation reservation = reservationService.findById(id);
        Theme theme = themeService.findById(reservation.getThemeId());

        return FindReservation.from(reservation, theme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
