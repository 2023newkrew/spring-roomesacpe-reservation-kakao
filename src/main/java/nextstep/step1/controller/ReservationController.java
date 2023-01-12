package nextstep.step1.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nextstep.step1.dto.ReservationDetail;
import nextstep.step1.dto.ReservationDto;
import nextstep.step1.service.ThemeReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ThemeReservationService themeReservationService;

    @GetMapping("/{id}")
    ResponseEntity<ReservationDetail> getReservations(@NonNull @PathVariable("id") Long id){
        ReservationDetail reservationDetail = themeReservationService.findById(id);
        if(reservationDetail == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(reservationDetail);
    }

    @PostMapping
    ResponseEntity<ReservationDetail> createReservation(@Valid @RequestBody ReservationDto reservationDto) {
        Long reservationId = themeReservationService.reserve(reservationDto);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> cancelReservation(@NonNull @PathVariable("id") Long id){
        try{
            themeReservationService.cancelById(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException illegalArgumentException){
            return ResponseEntity.notFound().build();
        }
    }
}
