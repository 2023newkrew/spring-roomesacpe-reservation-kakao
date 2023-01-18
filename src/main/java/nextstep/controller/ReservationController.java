package nextstep.controller;

import java.net.URI;
import nextstep.dto.ReservationRequestDto;
import nextstep.dto.ReservationResponseDto;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;
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
    private final ThemeService themeService;

    public ReservationController(ReservationService reservationService, ThemeService themeService) {
        this.reservationService = reservationService;
        this.themeService = themeService;
    }

    @PostMapping(value = "")
    public ResponseEntity createReservation(@RequestBody ReservationRequestDto reservationRequestDTO) {
        reservationService.validateCreateReservation(reservationRequestDTO);
        Theme theme = themeService.findById(reservationRequestDTO.getThemeId());
        Reservation reservation = reservationService.createReservation(reservationRequestDTO, theme);
        return ResponseEntity.created(URI.create(String.format("/reservations/%d",
                reservation.getId()))).build();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ReservationResponseDto> findReservation(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(ReservationResponseDto.of(reservationService.findReservationByID(id)));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteReservation(@PathVariable(value = "id") Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
