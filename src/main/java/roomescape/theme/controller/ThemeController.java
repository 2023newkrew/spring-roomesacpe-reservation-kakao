package roomescape.theme.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.theme.domain.Theme;
import roomescape.theme.service.ThemeService;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/themes")
    public ResponseEntity<String> createTheme(@RequestBody @Valid Theme theme) {
        Long themeId = themeService.createTheme(theme);
        return ResponseEntity.created(URI.create("/themes/" + themeId)).build();
    }

    @GetMapping("/themes/{id}")
    public ResponseEntity<Theme> findThemeById(@PathVariable("id") String themeId) {
        Theme theme = themeService.findById(themeId);
        return ResponseEntity.ok().body(theme);
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<String> deleteTheme(@PathVariable("id") String themeId) {
        themeService.deleteById(themeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
