package nextstep.web.controller;

import java.net.URI;
import java.util.List;
import nextstep.domain.Theme;
import nextstep.web.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("")
    public ResponseEntity createTheme(@RequestBody Theme theme) {
        Long id = themeService.createTheme(theme);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("")
    public ResponseEntity lookupAllThemes() {
        List<Theme> themes = themeService.lookupAllThemes();
        return ResponseEntity.ok().body(themes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTheme(@PathVariable Long id) {
        Theme theme = themeService.deleteTheme(id);
        return ResponseEntity.ok().body(theme);
    }
}
