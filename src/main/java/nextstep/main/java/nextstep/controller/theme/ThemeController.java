package nextstep.main.java.nextstep.controller.theme;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.domain.theme.ThemeCreateRequestDto;
import nextstep.main.java.nextstep.service.reservation.ReservationService;
import nextstep.main.java.nextstep.service.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ThemeCreateRequestDto request) {
        Long id = themeService.save(request);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }
}
