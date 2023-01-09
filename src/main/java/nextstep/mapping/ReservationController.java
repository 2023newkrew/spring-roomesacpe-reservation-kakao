package nextstep.mapping;

import java.util.ArrayList;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity requestParam(@RequestBody Reservation reservation) {
        reservations.add(new Reservation(
                ++reservationIdIndex,
                reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                theme
        ));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", String.format("/reservations/%d", reservationIdIndex))
                .build();
    }
}
