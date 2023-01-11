package nextstep.controller;

import nextstep.Theme;
import nextstep.domain.dto.GetReservationDTO;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.dto.CreateReservationDTO;
import nextstep.repository.WebAppReservationRepo;
import nextstep.service.WebAppReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private WebAppReservationService webAppReservationService;

    @PostMapping()
    public ResponseEntity createReservation(@RequestBody CreateReservationDTO reservationDto) {
        long id = webAppReservationService.addReservation(reservationDto);
        if (id == -1) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReservationDTO> getReservation(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(webAppReservationService.getReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        webAppReservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

}
