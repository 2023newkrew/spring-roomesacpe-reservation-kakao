package nextstep.web.controller;

import nextstep.domain.Reservation;
import nextstep.domain.dto.ReservationRequest;
import nextstep.domain.dto.ReservationResponse;
import nextstep.web.service.ReservationWebService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationWebService reservationWebService;

    public ReservationController(ReservationWebService reservationWebService) {
        this.reservationWebService = reservationWebService;
    }

    @PostMapping
    public ResponseEntity createReservation(@RequestBody ReservationRequest reservation){
        Reservation ret = this.reservationWebService.newReservation(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + ret.getId())).build();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ReservationResponse getReservation(@PathVariable long id){
        ReservationResponse reservationResponse = this.reservationWebService.findReservation(id);
        return reservationResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable long id){
        this.reservationWebService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body("IllegalArgumentException");
    }
}
