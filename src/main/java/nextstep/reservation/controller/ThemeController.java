package nextstep.reservation.controller;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ThemeRequestDto;
import nextstep.reservation.dto.ThemeResponseDto;
import nextstep.reservation.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<Object> addTheme(@RequestBody ThemeRequestDto themeRequestDto) {
        Long id = themeService.addTheme(themeRequestDto);

        return ResponseEntity
                .created(URI.create("/themes/" + id))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponseDto>> getAllThemes() {
        List<ThemeResponseDto> allThemes = themeService.getAllThemes();

        return ResponseEntity
                .ok()
                .body(allThemes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
