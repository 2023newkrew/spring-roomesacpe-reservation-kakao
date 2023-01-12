package nextstep.controller;

import nextstep.dao.ReservationDAO;
import nextstep.domain.Reservation;
import nextstep.domain.ReservationInfo;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationDAO reservationDAO;

    public ReservationController(ReservationService reservationService, ReservationDAO reservationJdbcTemplateDAO) {
        this.reservationService = reservationService;
        this.reservationDAO = reservationJdbcTemplateDAO;
    }

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody Reservation reservation) {
        Long id = reservationService.saveReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity showReservation(@PathVariable Long id) {
        Reservation reservation = reservationDAO.findById(id);
        ReservationInfo reservationInfo = new ReservationInfo(reservation);
        return ResponseEntity.ok().body(reservationInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        reservationDAO.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
