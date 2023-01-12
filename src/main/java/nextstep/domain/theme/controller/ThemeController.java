package nextstep.domain.theme.controller;

import nextstep.domain.theme.dto.ThemeRequestDto;
import nextstep.domain.theme.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("")
    public ResponseEntity<Void> add(@RequestBody ThemeRequestDto themeRequestDto) {
        Long id = themeService.add(themeRequestDto);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }
}
