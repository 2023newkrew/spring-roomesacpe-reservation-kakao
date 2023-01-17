package roomescape.reservation.controller;

import roomescape.reservation.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dao.ReservationDaoWeb;
import roomescape.reservation.dto.ReservationDto;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationDaoWeb reservationDAO;

    @PostMapping("/reservations")
    public ResponseEntity createReservation(@RequestBody ReservationDto reservationDto) {
        if (reservationDAO.findReservationByDateAndTime(reservationDto.getDate(), reservationDto.getTime()).size() > 0) {
            return ResponseEntity.unprocessableEntity().body("이미 예약된 시간입니다.");
        }
        Long id = reservationDAO.addReservation(new Reservation(reservationDto)).getId();
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity showReservation(@PathVariable Long id) {
        List<Reservation> reservations = reservationDAO.findReservationById(id);
        if (reservations.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 예약은 없는 예약입니다.");
        }
        return ResponseEntity.ok(reservations.get(0));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        reservationDAO.removeReservation(id);
        return ResponseEntity.noContent().build();
    }

}
