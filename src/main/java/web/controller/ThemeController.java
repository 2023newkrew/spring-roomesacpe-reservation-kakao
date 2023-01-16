package web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.dto.request.ThemeRequestDTO;
import web.dto.response.ThemeIdDto;
import web.service.ThemeService;

import java.net.URI;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequestDTO themeRequestDTO) {

        ThemeIdDto themeIdDto = themeService.createTheme(themeRequestDTO);

        return ResponseEntity.created(URI.create("/themes/" + themeIdDto.getId())).build();
    }

}
