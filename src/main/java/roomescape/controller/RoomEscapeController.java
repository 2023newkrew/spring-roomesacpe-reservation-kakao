package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomEscapeController {
    List<Reservation> reservationList = new ArrayList<>();

    //https://localhost:8080/reservations  / post data{}

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid Reservation reservation){
        // TODO: check duplicated time
        reservationList.add(reservation);


        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
//        return new ResponseEntity<Reservation>(HttpStatus.CREATED);
    }

}
