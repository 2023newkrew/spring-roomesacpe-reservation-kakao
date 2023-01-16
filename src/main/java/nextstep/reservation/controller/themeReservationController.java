package nextstep.reservation.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ReservationDetail;
import nextstep.reservation.dto.ReservationDto;
import nextstep.reservation.service.ThemeReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class themeReservationController {

    private final ThemeReservationService themeReservationService;

    @GetMapping("/{id}")
    ResponseEntity<ReservationDetail> getReservations(@NonNull @PathVariable("id") Long id){
        ReservationDetail reservationDetail = themeReservationService.findById(id);
        return ResponseEntity.ok().body(reservationDetail);
    }

    @PostMapping
    ResponseEntity<ReservationDetail> createReservation(@Valid @RequestBody ReservationDto reservationDto){
        Long reservationId = themeReservationService.reserve(reservationDto);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> cancelReservation(@NonNull @PathVariable("id") Long id){
        themeReservationService.cancelById(id);
        return ResponseEntity.noContent().build();
    }
}
