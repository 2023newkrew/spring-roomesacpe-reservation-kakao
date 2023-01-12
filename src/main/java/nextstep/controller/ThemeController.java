package nextstep.controller;

import nextstep.domain.Theme;
import nextstep.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("")
    public ResponseEntity createTheme(@RequestBody Theme theme) {
        Long id = themeService.saveTheme(theme);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("")
    public ResponseEntity showAllTheme() {
        List<Theme> themes = themeService.findAllTheme();
        return ResponseEntity.ok().body(themes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }
}
