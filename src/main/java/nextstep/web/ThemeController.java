package nextstep.web;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.model.Theme;
import nextstep.service.ThemeService;
import nextstep.web.dto.ThemeRequest;
import nextstep.web.dto.ThemeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest request) {
        Theme theme = themeService.save(request);
        return ResponseEntity.created(URI.create("/themes/" + theme.getId())).build();
    }

    @GetMapping("/themes")
    public ResponseEntity<List<ThemeResponse>> getThemes() {
        List<Theme> themes = themeService.getThemes();

        List<ThemeResponse> responses = themes.stream()
                .map(t -> new ThemeResponse(t.getId(), t.getName(), t.getDesc(), t.getPrice()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

}
