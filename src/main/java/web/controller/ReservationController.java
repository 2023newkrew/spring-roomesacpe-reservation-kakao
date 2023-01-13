package web.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.request.ReservationRequestDTO;
import web.dto.response.ReservationIdDto;
import web.dto.response.ReservationResponseDTO;
import web.service.ReservationService;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {

        ReservationIdDto reservationIdDto = reservationService.createReservation(reservationRequestDTO);

        return ResponseEntity.created(URI.create("/reservations/" + reservationIdDto.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> retrieveReservation(@PathVariable Long id) {

        ReservationResponseDTO reservationResponseDTO = reservationService.getReservationById(id);

        return ResponseEntity.ok()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(reservationResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {

        reservationService.deleteReservationById(id);

        return ResponseEntity.noContent().build();
    }

}
