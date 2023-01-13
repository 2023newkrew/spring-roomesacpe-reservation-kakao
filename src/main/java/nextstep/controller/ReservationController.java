package nextstep.controller;

import nextstep.domain.service.ReservationService;
import nextstep.domain.service.exception.DuplicateSaveException;
import nextstep.domain.service.exception.ResourceNotFoundException;
import nextstep.domain.dto.GetReservationDTO;
import nextstep.domain.dto.PostReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public ResponseEntity postReservation(@RequestBody PostReservationDTO reservationDto) {
        try {
            long id = reservationService.saveReservation(reservationDto);
            return ResponseEntity.created(URI.create("/reservations/" + id)).build();
        } catch (DuplicateSaveException e) {
            return ResponseEntity.badRequest().build();
        } catch (DataAccessException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReservationDTO> getReservation(@PathVariable("id") Long id) {
        try {
            GetReservationDTO getReservationDTO = new GetReservationDTO(reservationService.findReservation(id));
            return ResponseEntity.ok().body(getReservationDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataAccessException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataAccessException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
