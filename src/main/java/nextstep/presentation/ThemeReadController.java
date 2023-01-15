package nextstep.presentation;

import nextstep.dto.request.Pageable;
import nextstep.dto.response.FindThemeResponse;
import nextstep.presentation.argumentresolver.Pagination;
import nextstep.service.ThemeReadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeReadController {

    private final ThemeReadService themeReadService;

    public ThemeReadController(ThemeReadService themeReadService) {
        this.themeReadService = themeReadService;
    }

    @GetMapping(params = "name")
    public ResponseEntity<FindThemeResponse> findThemeByName(@RequestParam String name) {
        FindThemeResponse findThemeResponse = FindThemeResponse.from(themeReadService.findThemeByName(name));

        return ResponseEntity.ok(findThemeResponse);
    }

    @GetMapping(params = { "page", "size" })
    public ResponseEntity<List<FindThemeResponse>> findAllTheme(@Pagination Pageable pageable) {
        List<FindThemeResponse> findThemeResponses = themeReadService.findAllTheme(pageable);

        return ResponseEntity.ok(findThemeResponses);
    }

}
