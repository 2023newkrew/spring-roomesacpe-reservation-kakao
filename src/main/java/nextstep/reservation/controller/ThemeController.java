package nextstep.reservation.controller;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ThemeRequest;
import nextstep.reservation.dto.ThemeResponse;
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
    ResponseEntity create(@RequestBody ThemeRequest themeRequest) {
        ThemeResponse created = themeService.registerTheme(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + created.getId())).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<ThemeResponse> findById(@PathVariable long id) {
        ThemeResponse foundedTheme = themeService.findById(id);
        return ResponseEntity.ok(foundedTheme);
    }

    @GetMapping
    ResponseEntity<List<ThemeResponse>> findAll() {
        List<ThemeResponse> themeList = themeService.findAll();
        return ResponseEntity.ok().body(themeList);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteById(@PathVariable("id") long id) {
        themeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
