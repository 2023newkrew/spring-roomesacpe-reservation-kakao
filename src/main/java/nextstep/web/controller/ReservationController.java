package nextstep.web.controller;

import lombok.RequiredArgsConstructor;
import nextstep.web.dto.CreateReservationRequestDto;
import nextstep.web.dto.CreateReservationResponseDto;
import nextstep.web.dto.FindReservationResponseDto;
import nextstep.web.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private static final String BASE_URI = "/reservation";
    private final ReservationService reservationService;

    @PostMapping
    public Response<CreateReservationResponseDto> createReservation(@RequestBody CreateReservationRequestDto requestDto) {
        CreateReservationResponseDto location = new CreateReservationResponseDto(
                BASE_URI + "/" + reservationService.createReservation(requestDto)
        );

        return new Response<>(HttpStatus.OK.value(), HttpStatus.CREATED.name(), location);
    }

    @GetMapping("{id}")
    public Response<FindReservationResponseDto> findReservation(@PathVariable Long id) {
        FindReservationResponseDto reservation = reservationService.findReservation(id);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), reservation);
    }

    @DeleteMapping("{id}")
    public Response<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.NO_CONTENT.name(), null);
    }
}
