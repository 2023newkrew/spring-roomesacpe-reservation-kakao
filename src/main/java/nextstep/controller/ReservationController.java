package nextstep.controller;

import java.net.URI;
import java.sql.SQLException;
import nextstep.dto.ReservationRequestDTO;
import nextstep.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(value = "/reservations")
    public ResponseEntity createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO)
            throws SQLException {
        Long roomId = reservationService.createReservation(reservationRequestDTO);
        return ResponseEntity.created(URI.create(String.format("/reservations/%d", roomId))).build();
    }
}
