package nextstep.controller;

import nextstep.domain.theme.Theme;
import nextstep.domain.dto.GetReservationDTO;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.dto.CreateReservationDTO;
import nextstep.repository.WebAppReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private WebAppReservationRepo webAppReservationRepo;

    @PostMapping()
    public ResponseEntity createReservation(@RequestBody CreateReservationDTO reservationDto) {
        Reservation reservation = new Reservation(
                LocalDate.parse(reservationDto.getLocalDate()),
                LocalTime.parse(reservationDto.getLocalTime()),
                reservationDto.getName(),
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
        );
        if (webAppReservationRepo.countWhenDateAndTimeMatch(Date.valueOf(reservation.getDate()), Time.valueOf(reservation.getTime())) > 0) {
            return ResponseEntity.badRequest().build();
        }
        long id = webAppReservationRepo.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReservationDTO> getReservation(@PathVariable("id") Long id) {
        Reservation reservation = webAppReservationRepo.findById(id);
        if (reservation == null) {
            return ResponseEntity.badRequest().build();
        }
        GetReservationDTO getReservationDTO = new GetReservationDTO(reservation);
        return ResponseEntity.ok().body(getReservationDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        int result = webAppReservationRepo.delete(id);
        if (result == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

}
