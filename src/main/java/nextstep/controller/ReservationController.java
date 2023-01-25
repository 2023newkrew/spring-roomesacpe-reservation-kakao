package nextstep.controller;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.service.ReservationService;
import nextstep.domain.dto.GetReservationDTO;
import nextstep.domain.dto.PostReservationDTO;
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

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ResponseEntity postReservation(@RequestBody PostReservationDTO dto) {
        Reservation reservation = new Reservation(
                LocalDate.parse(dto.getLocalDate()),
                LocalTime.parse(dto.getLocalTime()),
                dto.getName(),
                dto.getThemeId());
        long id = reservationService.save(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReservationDTO> getReservation(@PathVariable("id") Long id) {
        GetReservationDTO getReservationDTO = new GetReservationDTO(reservationService.find(id));
        return ResponseEntity.ok().body(getReservationDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
