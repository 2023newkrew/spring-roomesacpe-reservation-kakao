package nextstep.presentation;

import nextstep.dto.request.CreateThemeRequest;
import nextstep.service.ThemeWriteService;
import nextstep.utils.ThemeRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/themes")
public class ThemeWriteController {

    private final ThemeWriteService themeWriteService;
    private final ThemeRequestValidator themeRequestValidator;

    public ThemeWriteController(ThemeWriteService themeService, ThemeRequestValidator themeRequestValidator) {
        this.themeWriteService = themeService;
        this.themeRequestValidator = themeRequestValidator;
    }

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody CreateThemeRequest createThemeRequest) {
        themeRequestValidator.validateCreateRequest(createThemeRequest);
        Long themeId = themeWriteService.createTheme(createThemeRequest);

        return ResponseEntity.created(URI.create("/themes/" + themeId))
                .build();
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> deleteThemeById(@PathVariable Long themeId) {
        themeWriteService.deleteThemeById(themeId);

        return ResponseEntity.noContent()
                .build();
    }

}
