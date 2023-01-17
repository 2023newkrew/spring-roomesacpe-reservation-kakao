package nextstep.web.presentation;

import nextstep.dto.CreateThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.dto.ThemesResponse;
import nextstep.exception.InvalidCreateThemeRequestException;
import nextstep.web.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<Void> createTheme(
            @Validated @RequestBody CreateThemeRequest request,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new InvalidCreateThemeRequestException();

        Long id = themeService.createTheme(request);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("/{themeId}")
    public ResponseEntity<ThemeResponse> findThemeById(@PathVariable Long themeId) {
        return ResponseEntity.ok(themeService.findThemeById(themeId));
    }

    @GetMapping
    public ResponseEntity<ThemesResponse> getAllThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> deleteThemeById(@PathVariable Long themeId) {
        themeService.deleteThemeById(themeId);
        return ResponseEntity.noContent().build();
    }
}
