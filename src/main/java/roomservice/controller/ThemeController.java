package roomservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomservice.domain.dto.ThemeCreateDto;
import roomservice.domain.dto.ThemeFindResultDto;
import roomservice.service.ThemeService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/themes")
@AllArgsConstructor
public class ThemeController {
    ThemeService themeService;

    @PostMapping("")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeCreateDto themeDto){
        System.out.println(themeDto.getName());
        long id = themeService.createTheme(themeDto);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("")
    public ResponseEntity<List<ThemeFindResultDto>> showTheme(){
        List<ThemeFindResultDto> result = themeService.findAllTheme();
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable("id") long id){
        themeService.deleteThemeById(id);
        return ResponseEntity.noContent().build();
    }
}
