package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.dto.ReservationRequest;
import nextstep.dto.ReservationResponse;
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
    public ResponseEntity createReservation(@RequestBody ReservationRequest reservation){
        Reservation ret = this.reservationService.newReservation(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + ret.getId())).build();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ReservationResponse getReservation(@PathVariable long id){
        ReservationResponse reservationResponse = this.reservationService.findReservation(id);
        return reservationResponse;
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
