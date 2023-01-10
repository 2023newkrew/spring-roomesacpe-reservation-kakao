package nextstep.controller;

import nextstep.Theme;
import nextstep.domain.dto.GetReservationDTO;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.dto.CreateReservationDTO;
import nextstep.domain.reservation.Reservations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @PostMapping()
    public ResponseEntity createReservation(@RequestBody CreateReservationDTO reservationDto) {
        Reservation reservation = new Reservation(
                reservationDto.getLocalDate(),
                reservationDto.getLocalTime(),
                reservationDto.getName(),
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
        );
        Reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/"+reservation.getId())).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReservationDTO> getReservation(@PathVariable("id") Long id) {
        Reservation reservation = Reservations.get(id);
        GetReservationDTO getReservationDTO = new GetReservationDTO(reservation);
        return ResponseEntity.ok().body(getReservationDTO);
    }

}
