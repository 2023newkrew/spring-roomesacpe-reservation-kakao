package nextstep.controller;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.service.ReservationService;
import nextstep.domain.service.exception.DuplicateSaveException;
import nextstep.domain.service.exception.ResourceNotFoundException;
import nextstep.domain.dto.GetReservationDTO;
import nextstep.domain.dto.PostReservationDTO;
import nextstep.domain.theme.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        try {
            Reservation reservation = new Reservation(
                    LocalDate.parse(dto.getLocalDate()),
                    LocalTime.parse(dto.getLocalTime()),
                    dto.getName(),
                    new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));
            long id = reservationService.save(reservation);
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
            GetReservationDTO getReservationDTO = new GetReservationDTO(reservationService.find(id));
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
            reservationService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataAccessException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
