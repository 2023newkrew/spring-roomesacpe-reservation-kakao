package nextstep.controller;

import lombok.RequiredArgsConstructor;
import nextstep.Theme;
import nextstep.dto.ThemeRequestDTO;
import nextstep.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/themes")
@RestController
public class ThemeController {
    private static final String THEME_PATH = "/themes";

    private final ThemeService service;

    @PostMapping
    public ResponseEntity<?> createTheme(@RequestBody ThemeRequestDTO request) {
        URI location = URI.create(THEME_PATH + "/" + service.create(request));

        return ResponseEntity.created(location)
                .build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Theme>> getThemeList() {

        return ResponseEntity.ok(service.getList());
    }

    @DeleteMapping("/{theme_id}")
    public ResponseEntity<?> deleteTheme(@PathVariable("theme_id") Long themeId) {
        service.deleteById(themeId);

        return ResponseEntity.noContent()
                .build();
    }
}
