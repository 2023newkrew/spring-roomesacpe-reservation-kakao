package nextstep.web.controller;

import lombok.RequiredArgsConstructor;
import nextstep.web.dto.CreateReservationRequestDto;
import nextstep.web.dto.CreateReservationResponseDto;
import nextstep.web.dto.FindReservationResponseDto;
import nextstep.web.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Response<CreateReservationResponseDto>> createReservation(@RequestBody CreateReservationRequestDto requestDto) {
        CreateReservationResponseDto location = new CreateReservationResponseDto(reservationService.createReservation(requestDto));

        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), HttpStatus.CREATED.name(), location));
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<FindReservationResponseDto>> findReservation(@PathVariable Long id) {
        FindReservationResponseDto reservation = reservationService.findReservation(id);

        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), reservation));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<Void>> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), null));
    }
}
