package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Object> reserve(@RequestBody Reservation reservation){
        Reservation confirmedReservation = reservationService.reserve(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + confirmedReservation.getId())).build();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Reservation findReservation(@PathVariable long id){
        return reservationService.findReservation(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelReservation(@PathVariable long id){
        boolean isDeleted = reservationService.cancelReservation(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
