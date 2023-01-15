package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.Theme;
import roomescape.service.theme.ThemeService;

@RestController
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @PostMapping
    public ResponseEntity<Object> createTheme(@RequestBody Theme theme) {
        long id = themeService.create(theme);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", String.format("/theme/%d", id))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<Theme>> listTheme() {
        List<Theme> themeList = themeService.list();
        return ResponseEntity.status(HttpStatus.OK).body(themeList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Theme> findTheme(@PathVariable Long id) {
        Theme theme = themeService.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(theme);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> removeTheme(@PathVariable Long id) {
        themeService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
