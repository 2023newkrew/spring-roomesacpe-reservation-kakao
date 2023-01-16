package nextstep.main.java.nextstep.mvc.controller.theme;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateOrUpdateRequest;
import nextstep.main.java.nextstep.mvc.service.reservation.ReservationService;
import nextstep.main.java.nextstep.mvc.service.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ThemeCreateOrUpdateRequest request) {
        Long id = themeService.save(request);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<?> findALl() {
        return ResponseEntity.ok(themeService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        return ResponseEntity.ok(themeService.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        themeService.deleteById(id, reservationService.existsByThemeId(id));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody ThemeCreateOrUpdateRequest request) {
        themeService.update(id, request);

        return ResponseEntity.noContent().build();
    }
}
