package kakao.controller;

import kakao.dto.request.CreateThemeRequest;
import kakao.dto.request.UpdateThemeRequest;
import kakao.dto.response.ThemeResponse;
import kakao.service.ThemeService;
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

    @PostMapping
    public ResponseEntity<Long> createTheme(@RequestBody CreateThemeRequest request) {
        long generatedId = themeService.createTheme(request);
        return ResponseEntity.created(URI.create("/themes/" + generatedId)).body(generatedId);
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> listThemes() {
        return ResponseEntity.ok(themeService.getThemes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponse> getTheme(@PathVariable Long id) {
        return ResponseEntity.ok(themeService.getTheme(id));
    }

    @PatchMapping
    public ResponseEntity<ThemeResponse> updateTheme(@RequestBody UpdateThemeRequest request) {
        return ResponseEntity.ok(themeService.updateTheme(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
