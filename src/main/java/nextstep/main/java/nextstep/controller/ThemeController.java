package nextstep.main.java.nextstep.controller;

import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.domain.ThemeCreateRequestDto;
import nextstep.main.java.nextstep.message.ControllerMessage;
import nextstep.main.java.nextstep.service.ThemeService;
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

    @PostMapping()
    public ResponseEntity<Theme> createTheme(@RequestBody ThemeCreateRequestDto themeCreateRequestDto) {
        Theme createdTheme = themeService.createTheme(themeCreateRequestDto);
        System.out.println("createdTheme = " + createdTheme);
        return ResponseEntity.created(URI.create("/themes/" + createdTheme.getId()))
                .build();
    }

    @GetMapping()
    public ResponseEntity<List<Theme>> findAllTheme() {
        return ResponseEntity.ok(themeService.findAllTheme());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.ok(ControllerMessage.DELETE_THEME_SUCCESS_MESSAGE);
    }
}
