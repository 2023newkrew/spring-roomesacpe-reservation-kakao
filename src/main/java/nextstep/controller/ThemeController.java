package nextstep.controller;

import nextstep.dto.request.CreateThemeRequest;
import nextstep.dto.response.ThemeResponse;
import nextstep.exception.ReservedThemeException;
import nextstep.service.ThemeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/themes")
@RestController
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("")
    public ResponseEntity<ThemeResponse> createTheme(@RequestBody CreateThemeRequest createThemeRequest) {
        ThemeResponse createdTheme = themeService.createTheme(createThemeRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/themes/" + createdTheme.getId())
                .body(createdTheme);
    }

    @GetMapping("")
    public ResponseEntity<List<ThemeResponse>> findThemes() {
        List<ThemeResponse> themes = themeService.findAllThemes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(themes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) throws ReservedThemeException {
        themeService.deleteTheme(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
