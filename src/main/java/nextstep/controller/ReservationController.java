package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationInfo;
import nextstep.domain.Theme;
import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.ReservationException;
import nextstep.dao.WebReservationDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final WebReservationDAO webReservationDAO;
    private final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    public ReservationController(WebReservationDAO reservationQueryingDAO) {
        this.webReservationDAO = reservationQueryingDAO;
    }

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody Reservation reservation) {
        List<Reservation> reservationsByDateAndTime = webReservationDAO.findReservationByDateAndTime(
                reservation.getDate(), reservation.getTime());
        if (reservationsByDateAndTime.size() > 0) {
            throw new ReservationException(ErrorCode.ALREADY_RESERVATION_EXISTS);
        }

        Reservation newReservation = new Reservation(reservation.getDate(), reservation.getTime(),
                reservation.getName(), theme);
        Long id = webReservationDAO.insertWithKeyHolder(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity lookupReservation(@PathVariable Long id) {
        Reservation reservation = webReservationDAO.findReservationById(id);
        ReservationInfo reservationInfo = new ReservationInfo(reservation);
        return ResponseEntity.ok().body(reservationInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity cancelReservation(@PathVariable Long id) {
        webReservationDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}
