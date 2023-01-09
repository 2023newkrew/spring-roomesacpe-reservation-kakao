package nextstep.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.BadRequestException;
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

    private void validateReservation(Reservation reservation) {
        if (!reservation.isValid()) {
            throw new BadRequestException();
        }
    }

    @PostMapping(value = "", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation) {
        validateReservation(reservation);
        if (reservations.stream().anyMatch(
                it -> it.getDate().equals(reservation.getDate()) && it.getTime()
                        .equals(reservation.getTime()))) {
            throw new BadRequestException();
        }
        reservations.add(
                new Reservation(++reservationIdIndex, reservation.getDate(), reservation.getTime(),
                        reservation.getName(), theme));
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", String.format("/reservations/%d", reservationIdIndex)).build();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Reservation> showReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream().filter(it -> Objects.equals(it.getId(), id))
                .findFirst().orElse(null);
        if (reservation == null) {
            throw new BadRequestException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(reservation);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        if (reservations.stream().noneMatch(v -> Objects.equals(v.getId(), id))) {
            throw new BadRequestException();
        }
        reservations.removeIf(reservation -> Objects.equals(reservation.getId(), id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
