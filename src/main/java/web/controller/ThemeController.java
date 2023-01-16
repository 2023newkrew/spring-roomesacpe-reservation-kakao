package web.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.request.ThemeRequestDTO;
import web.dto.response.ThemeIdDto;
import web.dto.response.ThemeResponseDTO;
import web.service.ThemeService;

import java.net.URI;
import java.util.List;

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

    @GetMapping("")
    public ResponseEntity<List<ThemeResponseDTO>> retrieveAllThemes() {
        List<ThemeResponseDTO> themes = themeService.findAllThemes();

        System.out.println(themes);

        return ResponseEntity.ok()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(themes);
    }

    @PutMapping("/{themeId}")
    public ResponseEntity<Void> updateTheme(@PathVariable Long themeId, @RequestBody ThemeRequestDTO themeRequestDTO) {
        themeService.updateTheme(themeId, themeRequestDTO);

        return ResponseEntity.ok().build();
    }

}
