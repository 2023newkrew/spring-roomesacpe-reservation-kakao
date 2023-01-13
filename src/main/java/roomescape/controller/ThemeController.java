package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.ThemeRequest;
import roomescape.controller.dto.ThemeResponse;
import roomescape.service.ThemeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> getThemes() {
        List<ThemeResponse> themeResponses = themeService.getAllThemes();
        return ResponseEntity.ok(themeResponses);
    }

    @GetMapping("/{themeId}")
    public ResponseEntity<ThemeResponse> getTheme(@PathVariable Long themeId) {
        ThemeResponse themeResponse = themeService.getTheme(themeId);
        return ResponseEntity.ok(themeResponse);
    }

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long themeId = themeService.createTheme(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + themeId)).build();
    }

    @PutMapping("/{themeId}")
    public ResponseEntity<Void> changeTheme(@PathVariable Long themeId,
                                            @RequestBody ThemeRequest themeRequest) {
        themeService.changeTheme(themeId, themeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long themeId) {
        themeService.deleteTheme(themeId);
        return ResponseEntity.noContent().build();
    }
}
