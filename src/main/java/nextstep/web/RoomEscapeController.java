package nextstep.web;

import nextstep.model.Reservation;
import nextstep.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
public class RoomEscapeController {
    private final ReservationRepository reservationRepository;

    @Autowired
    public RoomEscapeController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequest request) {
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        Reservation reservation = new Reservation(1L, request.getDate(), request.getTime(), request.getName(), theme);
        reservationRepository.save(reservation);
        return ResponseEntity.created(URI.create("/reservations/1")).build();
    }

}
