package nextstep.reservation.controller;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    @PostMapping
    ResponseEntity create(@RequestBody Theme theme) {
        Theme created = themeService.create(theme);
        return ResponseEntity.created(URI.create("/themes/" + created.getId())).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<Theme> findById(@PathVariable long id) {
        Theme foundedTheme = themeService.findById(id);
        return ResponseEntity.ok(foundedTheme);
    }

    @GetMapping
    ResponseEntity<List<Theme>> findAll() {
        List<Theme> themeList = themeService.findAll();
        return ResponseEntity.ok().body(themeList);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteById(@PathVariable("id") long id) {
        themeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
