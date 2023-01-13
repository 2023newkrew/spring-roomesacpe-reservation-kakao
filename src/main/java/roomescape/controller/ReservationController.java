package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDAO;
import roomescape.dto.Reservation;


@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationDAO reservationDAO;

    @PostMapping
    public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation) {
        long id = reservationDAO.addReservation(reservation);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", String.format("/reservations/%d", id))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Reservation> showReservation(@PathVariable Long id) {
        Reservation reservation = reservationDAO.findReservation(id);
        return ResponseEntity.status(HttpStatus.OK).body(reservation);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        reservationDAO.findReservation(id);
        reservationDAO.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
