package nextstep.main.java.nextstep.mvc.controller.theme;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeUpdateRequest;
import nextstep.main.java.nextstep.mvc.service.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ThemeCreateRequest request) {
        Long id = themeService.save(request);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<?> findALl() {
        return ResponseEntity.ok(themeService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        return ResponseEntity.ok(themeService.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        themeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody ThemeUpdateRequest themeUpdateRequest) {
        themeService.update(id, themeUpdateRequest);

        return ResponseEntity.noContent().build();
    }
}
