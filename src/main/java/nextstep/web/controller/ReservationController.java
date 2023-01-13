package nextstep.web.controller;

import java.net.URI;
import nextstep.domain.Reservation;
import nextstep.web.VO.ReservationRequestVO;
import nextstep.web.service.ReservationService;
import nextstep.util.ReservationInfoConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody ReservationRequestVO reservationRequestVO) {
        Long id = reservationService.createReservation(reservationRequestVO);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity lookupReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.lookupReservation(id);
        String reservationJSONString = ReservationInfoConverter.convertReservationToJSONString(reservation);
        return ResponseEntity.ok().body(reservationJSONString);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity cancelReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.deleteReservation(id);
        String reservationJSONString = ReservationInfoConverter.convertReservationToJSONString(reservation);
        return ResponseEntity.ok().body(reservationJSONString);
    }
}
