package reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reservation.model.dto.RequestTheme;
import reservation.service.ThemeService;

import java.net.URI;

@RestController
public class ThemeController {

    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<?> createTheme(@RequestBody RequestTheme requestTheme) {
        Long id = themeService.createTheme(requestTheme);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }
}
