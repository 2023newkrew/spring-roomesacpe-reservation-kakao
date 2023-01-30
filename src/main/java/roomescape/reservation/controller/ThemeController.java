package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Theme;
import roomescape.reservation.dto.ThemeDto;
import roomescape.reservation.service.ThemeService;

import java.net.URI;
import java.util.List;

@RestController
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/themes")
    public ResponseEntity createTheme(@RequestBody ThemeDto ThemeDto) {//테마를 생성한다 201
        Long id = themeService.addTheme(ThemeDto);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("/themes")
    public ResponseEntity showAllThemes() { //모든 테마를 조회한다 200
        List<Theme> themes = themeService.getAllTheme();
        return ResponseEntity.ok().body(themes);
    }

    @DeleteMapping("themes/{id}")
    public ResponseEntity deleteTheme(@PathVariable Long id) { //테마를 삭제한다
        themeService.removeTheme(id);
        return ResponseEntity.noContent().build();
    }

}
