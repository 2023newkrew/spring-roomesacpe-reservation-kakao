package nextstep.presentation;

import nextstep.dto.request.CreateThemeRequest;
import nextstep.service.ThemeService;
import nextstep.utils.ThemeRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;
    private final ThemeRequestValidator themeRequestValidator;

    public ThemeController(ThemeService themeService, ThemeRequestValidator themeRequestValidator) {
        this.themeService = themeService;
        this.themeRequestValidator = themeRequestValidator;
    }

    @PostMapping
    public ResponseEntity<Void> createTheme(@RequestBody CreateThemeRequest createThemeRequest) {
        themeRequestValidator.validateCreateRequest(createThemeRequest);
        Long themeId = themeService.createTheme(createThemeRequest);

        return ResponseEntity.created(URI.create("/themes" + themeId))
                .build();
    }

}
