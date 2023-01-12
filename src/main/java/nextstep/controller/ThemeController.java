package nextstep.controller;

import nextstep.dto.ThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("")
    public ResponseEntity<Void> create(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponse> retrieveOne(@PathVariable Long id) {
        ThemeResponse themeResponse = themeService.retrieveOne(id);
        return ResponseEntity.ok().body(themeResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<ThemeResponse>> retrieveAll() {
        List<ThemeResponse> themeResponses = themeService.retrieveAll();
        return ResponseEntity.ok().body(themeResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ThemeRequest themeRequest) {
        Long newId = themeService.update(id, themeRequest);
        if (id.equals(newId)) {
            return ResponseEntity.created(URI.create("/themes/" + newId)).build();
        }
        return ResponseEntity.ok().build();
    }
}
