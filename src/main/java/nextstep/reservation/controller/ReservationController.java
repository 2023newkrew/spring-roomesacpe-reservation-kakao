package nextstep.reservation.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.request.ReservationRequestDto;
import nextstep.reservation.dto.response.ReservationResponseDto;
import nextstep.reservation.service.ReservationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Object> addReservation(@RequestBody ReservationRequestDto requestDto) {
        Long id = reservationService.addReservation(requestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/" + id);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok().body(reservationService.getReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        return ResponseEntity.ok().body(reservationService.getAllReservation());
    }
}
