package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservation;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody CreateReservationRequest request) {
        try {
            Theme theme = themeService.findById(request.themeId);
            Long id = reservationService.createReservation(request.getDate(),
                    request.getTime(), request.getName(), theme);
            return ResponseEntity.created(URI.create("/reservations/" + id)).build();
        } catch (Exception e){
            return ResponseEntity.badRequest()
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity findReservationById(@PathVariable Long id) {
        try {
            Reservation reservation = reservationService.findById(id);
            return new ResponseEntity(reservation, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest()
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.badRequest()
                    .build();
        }
    }
}
