package nextstep.controller;

import nextstep.dao.ReservationDAO;
import nextstep.domain.Reservation;
import nextstep.domain.ReservationInfo;
import nextstep.domain.Theme;
import nextstep.exceptions.DataConflictException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationDAO reservationDAO;
    private final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    public ReservationController(ReservationDAO reservationJdbcTemplateDAO) {
        this.reservationDAO = reservationJdbcTemplateDAO;
    }

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody Reservation reservation) {
        List<Reservation> reservationsByDateAndTime = reservationDAO.findByDateAndTime(reservation.getDate(), reservation.getTime());
        if (reservationsByDateAndTime.size() > 0) {
            throw new DataConflictException("동일한 시간대에 예약이 이미 존재합니다.");
        }

        Reservation newReservation = new Reservation(reservation.getDate(), reservation.getTime(), reservation.getName(), theme);
        Long id = reservationDAO.save(newReservation);
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
