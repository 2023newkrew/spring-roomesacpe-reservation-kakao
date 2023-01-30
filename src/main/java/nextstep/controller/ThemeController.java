package nextstep.controller;

import nextstep.domain.Theme;
import nextstep.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity createTheme(@RequestBody Theme theme) {
        Long id = themeService.createTheme(theme);
        return ResponseEntity.created(URI.create("/themes/" + id))
                .build();
    }

    @GetMapping
    public ResponseEntity findAll() {
        try {
            List<Theme> themeList = themeService.findAll();
            return ResponseEntity.ok(themeList);
        } catch (Exception e){
            return ResponseEntity.badRequest()
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTheme(@PathVariable("id") Long id) {
        try {
            themeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (SQLException e) {
            return ResponseEntity.badRequest()
                    .build();
        }
    }
}
