package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Theme;
import roomescape.service.Theme.ThemeService;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ThemeController {
    final ThemeService themeService;

    @Autowired
    public ThemeController(@Qualifier("WebTheme")ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/themes")
    public ResponseEntity<String> createTheme(@RequestBody @Valid Theme theme){
        String userMessage = themeService.createTheme(theme);
        return ResponseEntity.created(URI.create("/themes/" + theme.getId())).body(userMessage);
    }

    @GetMapping("/themes/{id}")
    public ResponseEntity<String> lookUpTheme(@PathVariable("id") String themeId) {
        String userMessage = themeService.lookUpTheme(Long.valueOf(themeId));
        return ResponseEntity.ok().body(userMessage);
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<String> deleteTheme(@PathVariable("id") String deleteId) {
        themeService.deleteTheme(Long.valueOf(deleteId));
        return ResponseEntity.noContent().build();
    }
}
