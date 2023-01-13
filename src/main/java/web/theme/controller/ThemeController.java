package web.theme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.theme.dto.ThemeRequestDto;
import web.theme.dto.ThemeResponseDto;
import web.theme.service.ThemeService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequestMapping("/themes")
@RequiredArgsConstructor
@RestController
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid ThemeRequestDto requestDto) {
        long createdId = themeService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/themes/" + createdId))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponseDto>> findThemes() {
        List<ThemeResponseDto> findThemes = themeService.findThemes();

        return ResponseEntity.status(HttpStatus.OK)
                .body(findThemes);
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> deleteById(@PathVariable long themeId) {
        themeService.deleteById(themeId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
