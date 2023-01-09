package nextstep.controller;

import nextstep.Reservation;
import nextstep.dto.CreateReservationRequest;
import nextstep.service.RoomEscapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/reservations")
public class RoomEscapeController {

    private final RoomEscapeService roomEscapeService;

    @Autowired
    public RoomEscapeController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @PostMapping("")
    public Reservation createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        return roomEscapeService.add(createReservationRequest);
    }
}
