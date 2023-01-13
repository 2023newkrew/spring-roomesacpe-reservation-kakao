package kakao.controller;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import kakao.dto.request.CreateThemeRequest;
import kakao.dto.response.ThemeResponse;
import kakao.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<ThemeResponse> createTheme(@Valid @RequestBody CreateThemeRequest request) {
        ThemeResponse response = themeService.createTheme(request);
        return ResponseEntity.created(URI.create("")).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> getThemes() {
        return ResponseEntity.ok(themeService.getThemes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponse> getTheme(@PathVariable("id") @Min(1L) Long id) {
        return ResponseEntity.ok(themeService.getThemeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteTheme(@PathVariable("id") @Min(1L) Long id) {
        int deletedCount = themeService.deleteThemeById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedCount);
    }
}
