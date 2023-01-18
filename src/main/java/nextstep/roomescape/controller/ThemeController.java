package nextstep.roomescape.controller;

import nextstep.roomescape.exception.UsedExistEntityException;
import nextstep.roomescape.service.ReservationService;
import nextstep.roomescape.controller.RequestDTO.ThemeRequestDTO;
import nextstep.roomescape.repository.model.Theme;
import nextstep.roomescape.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;
    private final ReservationService reservationService;

    public ThemeController(ThemeService themeService, ReservationService reservationService) {
        this.themeService = themeService;
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequestDTO themeRequestDTO) {
        Long id = themeService.create(themeRequestDTO);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Theme>> showThemes() {
        List<Theme> results = themeService.findAll();
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        if (reservationService.findByThemeId(id).size() > 0){
            throw new UsedExistEntityException("해당 테마는 예약이 있어 삭제가 불가능합니다.");
        }
        themeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
