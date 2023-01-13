package nextstep.reservation.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ThemeRequestDto;
import nextstep.reservation.dto.ThemeResponseDto;
import nextstep.reservation.service.ThemeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<Object> addTheme(@RequestBody ThemeRequestDto themeRequestDto) {
        Long id = themeService.addTheme(themeRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/themes/" + id);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponseDto> getTheme(@PathVariable Long id) {
        return ResponseEntity.ok().body(themeService.getTheme(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponseDto>> getThemes() {
        return ResponseEntity.ok().body(themeService.getAllTheme());
    }

}
