package nextstep.controller;

import nextstep.domain.dto.ThemeRequest;
import nextstep.domain.dto.ThemeResponse;
import nextstep.domain.service.ThemeService;
import nextstep.domain.theme.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping()
    public ResponseEntity postReservation(@RequestBody ThemeRequest dto) {
        Theme theme = new Theme(
                dto.getName(),
                dto.getDesc(),
                dto.getPrice());
        long id = themeService.save(theme);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ThemeResponse>> getReservation() {
        List<Theme> themes = themeService.findAll();
        List<ThemeResponse> responseBody = new ArrayList<>();
        for (Theme theme : themes) {
            responseBody.add(new ThemeResponse(theme));
        }
        return ResponseEntity.ok().body(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") Long id) {
        themeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
