package nextstep.presentation;

import nextstep.dto.CreateThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody CreateThemeRequest request) {
        Long id = themeService.createTheme(request);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("/{themeId}")
    public ResponseEntity<ThemeResponse> findThemeById(@PathVariable Long themeId) {
        return ResponseEntity.ok(themeService.findThemeById(themeId));
    }
}
