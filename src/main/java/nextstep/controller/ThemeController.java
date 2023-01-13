package nextstep.controller;

import nextstep.domain.dto.theme.CreateThemeDto;
import nextstep.domain.dto.theme.UpdateThemeDto;
import nextstep.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping()
    public ResponseEntity addTheme(@RequestBody CreateThemeDto createThemeDto) {
        try {
            long id = themeService.addTheme(createThemeDto);
            return ResponseEntity.created(URI.create("/themes/" + id)).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllThemes() {
        return ResponseEntity.ok().body(themeService.getAllThemes());
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTheme(
            @PathVariable("id") Long id,
            @RequestBody UpdateThemeDto updateThemeDto) {
        try {
            themeService.updateTheme(updateThemeDto);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTheme(@PathVariable("id") Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }
}
