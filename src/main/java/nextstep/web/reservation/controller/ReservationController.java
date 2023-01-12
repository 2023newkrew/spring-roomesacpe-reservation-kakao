package nextstep.web.reservation.controller;

import lombok.RequiredArgsConstructor;
import nextstep.web.common.controller.Response;
import nextstep.web.reservation.dto.CreateReservationRequestDto;
import nextstep.web.reservation.dto.CreateReservationResponseDto;
import nextstep.web.reservation.dto.FindReservationResponseDto;
import nextstep.web.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public Response<CreateReservationResponseDto> createReservation(@RequestBody @Valid CreateReservationRequestDto requestDto) {
        return new Response<>(HttpStatus.OK.value(), HttpStatus.CREATED.name(), reservationService.createReservation(requestDto));
    }

    @GetMapping("{id}")
    public Response<FindReservationResponseDto> findReservation(@PathVariable @Min(value = 1, message = "ID를 확인해 주세요") Long id) {
        FindReservationResponseDto reservation = reservationService.findReservation(id);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), reservation);
    }

    @DeleteMapping("{id}")
    public Response<Void> deleteReservation(@PathVariable @Min(value = 1, message = "ID를 확인해 주세요") Long id) {
        reservationService.deleteReservation(id);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.NO_CONTENT.name(), null);
    }
}
