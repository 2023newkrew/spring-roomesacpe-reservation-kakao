package nextstep.controller;

import nextstep.domain.dto.GetReservationDto;
import nextstep.domain.dto.CreateReservationDto;
import nextstep.exception.DuplicateTimeReservationException;
import nextstep.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ResponseEntity createReservation(@RequestBody CreateReservationDto reservationDto) {
        try {
            long id = reservationService.addReservation(reservationDto);
            return ResponseEntity.created(URI.create("/reservations/" + id)).build();
        } catch (DuplicateTimeReservationException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReservationDto> getReservation(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(reservationService.getReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
