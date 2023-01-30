package nextstep.controller;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.service.ReservationService;
import nextstep.domain.dto.ReservationResponse;
import nextstep.domain.dto.ReservationRequest;
import nextstep.domain.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

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

    @PostMapping()
    public ResponseEntity postReservation(@RequestBody ReservationRequest dto) {
        Reservation reservation = new Reservation(
                LocalDate.parse(dto.getLocalDate()),
                LocalTime.parse(dto.getLocalTime()),
                dto.getName(),
                dto.getThemeId());
        long id = reservationService.save(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable("id") Long id) {
        Reservation reservation = reservationService.find(id);
        ReservationResponse dto = new ReservationResponse(reservation, themeService.find(reservation.getThemeId()));
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
