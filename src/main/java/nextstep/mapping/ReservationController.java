package nextstep.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.http.HttpStatus;
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

    private List<Reservation> reservations = new ArrayList<>();
    private Long reservationIdIndex = 0L;
    private Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    @PostMapping("")
    public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation) {
        reservations.add(
                new Reservation(++reservationIdIndex, reservation.getDate(), reservation.getTime(),
                        reservation.getName(), theme));
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", String.format("/reservations/%d", reservationIdIndex)).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Reservation> showReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream().filter(it -> Objects.equals(it.getId(), id))
                .findFirst().orElse(null);
        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        if (reservations.stream().noneMatch(v -> Objects.equals(v.getId(), id))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        reservations.removeIf(reservation -> Objects.equals(reservation.getId(), id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
