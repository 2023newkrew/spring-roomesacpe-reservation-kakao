package nextstep.web;

import nextstep.dto.ThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ThemeController {
    private final ThemeService themeService;
    private final ReservationService reservationService;

    public ThemeController(ThemeService themeService, ReservationService reservationService) {
        this.themeService = themeService;
        this.reservationService = reservationService;
    }

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest request) {
        Theme theme = themeService.createTheme(request);
        return ResponseEntity.created(URI.create("/themes/" + theme.getId())).build();
    }

    @GetMapping("/themes/{id}")
    public ResponseEntity<ThemeResponse> getTheme(@PathVariable Long id) {
        Theme theme = themeService.getTheme(id);
        return ResponseEntity.ok(new ThemeResponse(theme));
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void>  deleteTheme(@PathVariable Long id) {
        List<Reservation> reservationsInTheme = reservationService.getReservationByThemeId(id);
        themeService.deleteTheme(id, reservationsInTheme);
        return ResponseEntity.noContent().build();
    }

}
