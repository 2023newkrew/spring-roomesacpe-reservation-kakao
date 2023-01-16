package nextstep.controller;

import nextstep.dto.CreateThemeRequest;
import nextstep.dto.FindTheme;
import nextstep.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }


    @PostMapping("")
    public ResponseEntity createTheme(@RequestBody CreateThemeRequest request) {
        Long id = themeService.createTheme(request.getName(), request.getDesc(), request.getPrice());
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("")
    public List<FindTheme> findAllThemes() {
        return themeService.findAll().stream()
                .map(theme -> FindTheme.from(theme))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteThemeById(@PathVariable("id") Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }

}
