package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ThemeRequestDto;
import roomescape.dto.ThemeResponseDto;
import roomescape.service.ThemeService;

import java.net.URI;

@RestController
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("/themes/{id}")
    public ResponseEntity<ThemeResponseDto> findTheme(@PathVariable("id") Long themeId) {
        ThemeResponseDto themeResponseDto = themeService.findTheme(themeId);
        return ResponseEntity.ok(themeResponseDto);
    }

    @PostMapping("/themes")
    public ResponseEntity createTheme(@RequestBody ThemeRequestDto themeRequestDto) {
        Long themeId = themeService.createTheme(themeRequestDto);
        return ResponseEntity.created(URI.create("/themes/" + themeId)).build();
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity deleteTheme(@PathVariable("id") Long themeId) {
        themeService.deleteTheme(themeId);
        return ResponseEntity.noContent().build();
    }
}
