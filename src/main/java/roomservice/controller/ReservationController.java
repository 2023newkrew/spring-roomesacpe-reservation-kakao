package roomservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomservice.domain.dto.ReservationCreateDto;
import roomservice.domain.dto.ReservationFoundDto;
import roomservice.service.ReservationService;

import javax.validation.Valid;
import java.net.URI;

/**
 * ReservationController processes various HTTP requests
 * related to reservations, including create, show, and delete methods.
 */
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Add a reservation to this program.
     *
     * @param reservationDto information of reservation to be added.
     * @return "created" response with id resulted by the program.
     */
    @PostMapping()
    public ResponseEntity<Void> createReservation(@RequestBody @Valid ReservationCreateDto reservationDto) {
        Long id = reservationService.createReservation(reservationDto);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    /**
     * show a reservation which have specific id.
     *
     * @param id which you want to find.
     * @return "ok" response with information of reservation if successfully found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationFoundDto> showReservation(@PathVariable Long id) {
        return ResponseEntity.ok().body(reservationService.findReservation(id));
    }

    /**
     * Delete a reservation from this program.
     *
     * @param id which you want to delete.
     * @return 204(No Content) (whether deleted or not)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
