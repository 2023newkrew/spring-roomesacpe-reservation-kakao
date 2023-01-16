package roomescape.theme.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.theme.dto.ThemeRequestDto;
import roomescape.theme.dto.ThemeResponseDto;
import roomescape.theme.service.ThemeService;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping()
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequestDto themeRequestDto) {
        long id = themeService.createTheme(themeRequestDto);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping()
    public ResponseEntity<List<ThemeResponseDto>> findAll() {
        return ResponseEntity.ok().body(themeService.findAllThemes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponseDto> findById(@PathVariable Long id) {
        ThemeResponseDto responseDto = themeService.findThemeById(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        themeService.removeThemeById(id);
        return ResponseEntity.noContent().build();
    }
}
