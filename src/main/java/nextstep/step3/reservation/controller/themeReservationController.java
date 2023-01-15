package nextstep.step3.reservation.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nextstep.step3.reservation.dto.ReservationDetail;
import nextstep.step3.reservation.dto.ReservationDto;
import nextstep.step3.reservation.service.ThemeReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class themeReservationController {

    private final ThemeReservationService themeReservationService;

    @GetMapping("/{id}")
    ResponseEntity<ReservationDetail> getReservations(@NonNull @PathVariable("id") Long id) throws SQLException{
        ReservationDetail reservationDetail = themeReservationService.findById(id);
        if(reservationDetail == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(reservationDetail);
    }

    @PostMapping
    ResponseEntity<ReservationDetail> createReservation(@Valid @RequestBody ReservationDto reservationDto) throws SQLException{
        Long reservationId = themeReservationService.reserve(reservationDto);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> cancelReservation(@NonNull @PathVariable("id") Long id){
        try{
            themeReservationService.cancelById(id);
            return ResponseEntity.noContent().build();
        }catch (SQLException sqlException){
            return ResponseEntity.notFound().build();
        }
    }
}
