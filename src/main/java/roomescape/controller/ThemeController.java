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
        Theme userTheme = themeService.createTheme(theme);
        return ResponseEntity.created(URI.create("/themes/" + theme.getId()))
                .body(userTheme.createMessage(theme.getId()));
    }

    @GetMapping("/themes/{id}")
    public ResponseEntity<String> lookUpTheme(@PathVariable("id") String themeId) {
        Theme userTheme = themeService.lookUpTheme(Long.valueOf(themeId));
        return ResponseEntity.ok().body(userTheme.toMessage());
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<String> deleteTheme(@PathVariable("id") String deleteId) {
        themeService.deleteTheme(Long.valueOf(deleteId));
        return ResponseEntity.noContent().build();
    }
}
