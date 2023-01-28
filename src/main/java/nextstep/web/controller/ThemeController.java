package nextstep.web.controller;

import lombok.RequiredArgsConstructor;
import nextstep.web.dto.ThemeRequestDto;
import nextstep.web.dto.ThemeResponseDto;
import nextstep.web.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ThemeRequestDto requestDto) {
        Long generatedId = themeService.create(requestDto);

        return ResponseEntity.created(URI.create("/themes/" + generatedId))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponseDto>> readAll() {
        return ResponseEntity.ok(themeService.readAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        themeService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }
}
