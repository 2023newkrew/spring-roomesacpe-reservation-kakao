package roomescape.theme.controller;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.theme.dto.request.ThemeRequestDTO;
import roomescape.theme.dto.response.ThemeResponseDTO;
import roomescape.theme.service.ThemeService;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(final ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<ThemeResponseDTO> createTheme(@RequestBody @Valid ThemeRequestDTO themeRequestDTO) {
        ThemeResponseDTO themeResponseDTO = this.themeService.save(themeRequestDTO);

        return ResponseEntity.created(URI.create("/themes/" + themeResponseDTO.getId())).body(themeResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponseDTO>> getAll() {
        final List<ThemeResponseDTO> themes = this.themeService.findAll();

        return ResponseEntity.ok(themes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        this.themeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
