package nextstep.controller;

import java.net.URI;
import java.sql.SQLException;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import nextstep.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(value = "")
    public ResponseEntity createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO)
            throws SQLException {
        Long roomId = reservationService.createReservation(reservationRequestDTO);
        return ResponseEntity.created(URI.create(String.format("/reservations/%d", roomId))).build();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ReservationResponseDTO> findReservation(@PathVariable(value = "id") Long id)
            throws SQLException {
        ReservationResponseDTO response = reservationService.findReservation(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteReservation(@PathVariable(value = "id") Long id) throws SQLException {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
