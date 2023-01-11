package nextstep.main.java.nextstep.controller.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.domain.reservation.ReservationCreateRequest;
import nextstep.main.java.nextstep.domain.reservation.ReservationFindResponse;
import nextstep.main.java.nextstep.service.reservation.ReservationService;
import nextstep.main.java.nextstep.service.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationCreateRequest request) {
        Long id = reservationService.save(request);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findOneById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        reservationService.deleteOneById(id);
        return ResponseEntity.noContent().build();
    }
}
