package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservation;
import nextstep.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody CreateReservationRequest request) {
        // 현재는 테마 커스터마이징 관련 기능이 필요 없으므로 하드코딩
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Long id = reservationService.createReservation(request.getDate(),
                request.getTime(), request.getName(), theme);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public FindReservation findReservationById(@PathVariable Long id) {

        Reservation reservation = reservationService.findById(id);
        return FindReservation.from(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
