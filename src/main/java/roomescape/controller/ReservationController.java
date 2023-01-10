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
import roomescape.exception.BadRequestException;


@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationDAO reservationDAO;

    private void validateReservation(Reservation reservation) {
        if (!reservation.isValid()) {
            throw new BadRequestException();
        }
        if (reservationDAO.findCountReservationByDateTime(reservation.getDate(),
                reservation.getTime()) == 1) {
            throw new BadRequestException();
        }
    }

    @PostMapping(value = "", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation) {
        validateReservation(reservation);
        long id = reservationDAO.addReservation(reservation);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", String.format("/reservations/%d", id))
                .build();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Reservation> showReservation(@PathVariable Long id) {
        try {
            Reservation reservation = reservationDAO.findReservation(id);
            return ResponseEntity.status(HttpStatus.OK).body(reservation);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        try {
            reservationDAO.findReservation(id);
            reservationDAO.deleteReservation(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
}
