package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ThemeRequestDto;
import roomescape.dto.ThemeResponseDto;
import roomescape.dto.ThemesResponseDto;
import roomescape.service.ThemeService;

import java.net.URI;

@Controller
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("/themes")
    public ResponseEntity<ThemesResponseDto> findTheme() {
        ThemesResponseDto themesResponseDto = themeService.findThemes();
        return ResponseEntity.ok(themesResponseDto);
    }

    @PostMapping("/themes")
    public ResponseEntity<ThemeResponseDto> createTheme(@RequestBody ThemeRequestDto req) {
        ThemeResponseDto res = themeService.createTheme(req);
        String id = res.getId().toString();
        return ResponseEntity.created(URI.create("/themes/").resolve(id)).body(res);
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity cancelTheme(@PathVariable("id") Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }
}
