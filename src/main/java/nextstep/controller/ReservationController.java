package nextstep.controller;

import nextstep.dto.Reservation;
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
    public ResponseEntity createReservation(@RequestBody Reservation reservation){
        Reservation ret = this.reservationService.reserve(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + ret.getId())).build();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Reservation getReservation(@PathVariable long id){
        Reservation reservation = this.reservationService.findReservation(id);
        return reservation;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable long id){
        this.reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body("IllegalArgumentException");
    }
}
