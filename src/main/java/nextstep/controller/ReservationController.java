package nextstep.controller;

import nextstep.Reservation;
import nextstep.Theme;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservation;
import nextstep.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody CreateReservationRequest request) {
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Reservation reservation = reservationService.createReservation(request.getDate(),
                request.getTime(), request.getName(), theme);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }

    @GetMapping("/{id}")
    public FindReservation findReservationById(@PathVariable Long id) {

        Reservation reservation = reservationService.findById(id);
        return FindReservation.from(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
