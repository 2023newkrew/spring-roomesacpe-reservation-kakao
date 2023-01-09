package nextstep.controller;

import nextstep.dto.ReservationRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @PostMapping
    public void addReservation(@RequestBody ReservationRequestDto requestDto) {

    }

}
