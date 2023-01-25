package nextstep.controller;

import nextstep.domain.Theme;
import nextstep.dto.ThemeDto;
import nextstep.service.ThemeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/themes")
@RestController
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("")
    public ResponseEntity<Void> themeAdd(@RequestBody ThemeDto themeDto) {
        themeService.save(themeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theme> themeDetails(@PathVariable Long id) {
        return ResponseEntity
                .ok(themeService.findById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Theme>> themeList() {
        return ResponseEntity
                .ok(themeService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> themeModify(@PathVariable Long id, @RequestBody ThemeDto themeDto) {
        themeService.update(id, themeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> themeRemove(@PathVariable Long id) {
        themeService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}