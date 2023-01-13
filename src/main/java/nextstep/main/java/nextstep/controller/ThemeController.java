package nextstep.main.java.nextstep.controller;

import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.domain.ThemeCreateRequestDto;
import nextstep.main.java.nextstep.service.ThemeService;
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

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping()
    public ResponseEntity<Theme> createTheme(@RequestBody ThemeCreateRequestDto themeCreateRequestDto) {
        Theme createdTheme = themeService.createTheme(themeCreateRequestDto);
        System.out.println("createdTheme = " + createdTheme);
        return ResponseEntity.created(URI.create("/themes/" + createdTheme.getId()))
                .build();
    }
}
