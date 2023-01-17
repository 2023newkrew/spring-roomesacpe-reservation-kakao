package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservation;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ThemeService themeService;

    @Autowired
    public ReservationController(ReservationService reservationService, ThemeService themeService) {
        this.reservationService = reservationService;
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity createReservation(@RequestBody CreateReservationRequest request) {
        // request 내의 themeId로 findById 메서드를 호출함으로서 존재하지 않는 theme에 대한 reservation 생성을 요청한 것은 아닌지 확인
        themeService.findById(request.getThemeId());
        Long id = reservationService.createReservation(request);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public FindReservation findReservationById(@PathVariable Long id) {

        Reservation reservation = reservationService.findById(id);
        Theme theme = themeService.findById(reservation.getThemeId());

        return FindReservation.from(reservation, theme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
