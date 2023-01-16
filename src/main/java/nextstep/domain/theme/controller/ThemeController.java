package nextstep.domain.theme.controller;

import nextstep.domain.theme.domain.Theme;
import nextstep.domain.theme.dto.ThemeRequestDto;
import nextstep.domain.theme.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("")
    public ResponseEntity<Void> add(@RequestBody ThemeRequestDto themeRequestDto) {
        Long id = themeService.add(themeRequestDto);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Theme> retrieve(@PathVariable Long id) {
        Theme theme = themeService.retrieve(id);
        return ResponseEntity.ok().body(theme);
    }

    @GetMapping("")
    public ResponseEntity<List<Theme>> retrieveAll() {
        List<Theme> themes = themeService.retrieveAll();
        return ResponseEntity.ok().body(themes);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody ThemeRequestDto themeRequestDto) {
        themeService.update(id, themeRequestDto);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }
}
