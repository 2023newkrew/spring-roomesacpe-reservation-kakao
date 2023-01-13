package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.dto.ThemeRequest;
import roomescape.dto.ThemeResponse;
import roomescape.service.ThemeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService){
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Long> createTheme(
            @RequestBody ThemeRequest themeRequest,
            HttpServletRequest httpServletRequest
    ){
        Long themeId = themeService.createTheme(themeRequest);
        String currentURL = httpServletRequest.getRequestURL().toString();

        return ResponseEntity.created(
                UriComponentsBuilder.fromHttpUrl(currentURL)
                        .path("/{id}")
                        .buildAndExpand(themeId)
                        .toUri()
        ).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponse> getTheme(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                themeService.getTheme(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(
            @PathVariable Long id
    ){
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }
}
