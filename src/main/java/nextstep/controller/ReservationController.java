package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.dto.ReservationDTO;
import nextstep.dto.ReservationVO;
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
    public ResponseEntity createReservation(@RequestBody ReservationDTO reservation){
        Reservation ret = this.reservationService.newReservation(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + ret.getId())).build();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ReservationVO getReservation(@PathVariable long id){
        ReservationVO reservationVO = this.reservationService.findReservation(id);
        return reservationVO;
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
