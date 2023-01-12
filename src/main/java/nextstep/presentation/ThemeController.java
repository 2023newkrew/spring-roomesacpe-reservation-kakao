package nextstep.presentation;

import nextstep.dto.request.CreateThemeRequest;
import nextstep.dto.request.Pageable;
import nextstep.dto.response.FindThemeResponse;
import nextstep.presentation.argumentresolver.Pagination;
import nextstep.service.ThemeService;
import nextstep.utils.ThemeRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping(params = "name")
    public ResponseEntity<FindThemeResponse> findThemeByName(@RequestParam String name) {
        FindThemeResponse findThemeResponse = FindThemeResponse.from(themeService.findThemeByName(name));

        return ResponseEntity.ok(findThemeResponse);
    }

    @GetMapping(params = { "page", "size" })
    public ResponseEntity<List<FindThemeResponse>> findAllTheme(@Pagination Pageable pageable) {
        List<FindThemeResponse> findThemeResponses = themeService.findAllTheme(pageable);

        return ResponseEntity.ok(findThemeResponses);
    }

}
