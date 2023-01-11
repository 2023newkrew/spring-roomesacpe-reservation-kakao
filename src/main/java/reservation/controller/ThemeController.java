package reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservation.domain.Theme;
import reservation.domain.dto.ThemeDto;
import reservation.service.ThemeService;

import java.net.URI;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("")
    public ResponseEntity<?> postTheme(@RequestBody ThemeDto ThemeDto) {
        Long id = themeService.createTheme(ThemeDto);
        return ResponseEntity.created(URI.create("/Themes/" + id)).build();
    }

    @GetMapping("/{themeId}")
    public ResponseEntity<?> getTheme(@PathVariable Long themeId) {
        Theme theme = themeService.getTheme(themeId);
        if (theme == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(theme);
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<?> deleteTheme(@PathVariable Long themeId) {
        themeService.deleteTheme(themeId);
        return ResponseEntity.noContent().build();
    }
}
