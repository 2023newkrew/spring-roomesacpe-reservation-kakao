package roomescape.controller;

import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.repository.JdbcReservationRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class RoomEscapeController {

    final JdbcReservationRepository jdbcReservationRepository;

    public RoomEscapeController(JdbcReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @PostMapping("/reservations")
        public ResponseEntity<String> createReservation(@RequestBody @Valid Reservation reservation){
        if (jdbcReservationRepository.findByDateAndTime(reservation) == 1) {
            throw new DuplicateRequestException("요청 날짜/시간에 이미 예약이 있습니다.");
        }
        Long reserveId = jdbcReservationRepository.createReservation(reservation);
        if (reserveId > 0){
            System.out.println(reservation.getName() + "님의 예약이 등록되었습니다.");
        }
        String retString = "Location: /reservation/" + reserveId;
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(retString);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Optional<Reservation>> lookUpReservation(@PathVariable("id") String reservationId) {
        Optional<Reservation> reservation = jdbcReservationRepository.findById(Integer.parseInt(reservationId));
        if (reservation.isPresent()) {
            return ResponseEntity.ok().body(reservation);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") String deleteId) {
        Integer deleteResult = jdbcReservationRepository.deleteReservation(Integer.parseInt(deleteId));
        if (deleteResult == 1) {
            System.out.println("Id: " + deleteResult + "의 예약이 취소되었습니다.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
