package nextstep.controller;

import nextstep.Reservation;
import nextstep.ReservationInfo;
import nextstep.Theme;
import nextstep.exceptions.CustomException;
import nextstep.web.ReservationQueryingDAO;
import nextstep.web.ReservationUpdatingDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationQueryingDAO reservationQueryingDAO;
    private final ReservationUpdatingDAO reservationUpdatingDAO;
    private final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    public ReservationController(ReservationQueryingDAO reservationQueryingDAO, ReservationUpdatingDAO reservationUpdatingDAO) {
        this.reservationQueryingDAO = reservationQueryingDAO;
        this.reservationUpdatingDAO = reservationUpdatingDAO;
    }

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody Reservation reservation) {
        List<Reservation> reservationsByDateAndTime = reservationQueryingDAO.findReservationByDateAndTime(reservation.getDate(), reservation.getTime());
        if (reservationsByDateAndTime.size() > 0) {
            throw new CustomException();
        }

        Reservation newReservation = new Reservation(reservation.getDate(), reservation.getTime(), reservation.getName(), theme);
        Long id = reservationUpdatingDAO.insertWithKeyHolder(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity showReservation(@PathVariable Long id) {
        Reservation reservation = reservationQueryingDAO.findReservationById(id);
        ReservationInfo reservationInfo = new ReservationInfo(reservation);
        return ResponseEntity.ok().body(reservationInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        reservationUpdatingDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}
