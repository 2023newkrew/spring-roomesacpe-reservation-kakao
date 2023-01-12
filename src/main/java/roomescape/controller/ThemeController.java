package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Theme;
import roomescape.dto.ThemeRequest;
import roomescape.service.ThemeService;

import java.net.URI;

@RestController
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/theme")
    public ResponseEntity createTheme(@RequestBody ThemeRequest themeRequest) {
        Theme theme = themeService.createTheme(themeRequest);
        return ResponseEntity.created(URI.create("/theme/" + theme.getId())).build();
    }

    @GetMapping("/theme/{id}")
    public ResponseEntity showTheme(@PathVariable Long id) {
        Theme theme = themeService.showTheme(id);
        if (theme == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 테마는 없는 테마입니다.");
        }
        return ResponseEntity.ok(theme);
    }

    @PutMapping("/theme")
    public ResponseEntity updateTheme(@RequestBody Theme theme) {
        Theme modifiedTheme = themeService.updateTheme(theme);
        if (modifiedTheme == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 테마는 없는 테마입니다.");
        }
        return ResponseEntity.ok(modifiedTheme);
    }

    @DeleteMapping("/theme/{id}")
    public ResponseEntity deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }

}