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


/**
 * ThemeController processes various HTTP requests
 * related to themes, including create, show, and delete methods.
 */
@Controller
@RequestMapping("/themes")
@AllArgsConstructor
public class ThemeController {
    ThemeService themeService;

    /**
     * Add a theme to this program.
     * @param themeDto information of theme to be added.
     * @return created(201) response if successfully proceeded.
     */
    @PostMapping("")
    public ResponseEntity<Void> createTheme(@RequestBody @Valid ThemeCreateDto themeDto){
        System.out.println(themeDto.getName());
        long id = themeService.createTheme(themeDto);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    /**
     * Show themes of this program.
     * @return ok(200) response with themes contents if successfully proceeded.
     */
    @GetMapping("")
    public ResponseEntity<List<ThemeFindResultDto>> showTheme(){
        List<ThemeFindResultDto> result = themeService.findAllTheme();
        return ResponseEntity.ok().body(result);
    }

    /**
     * Delete one theme from this program.
     * @return NoContent(204).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable("id") long id){
        themeService.deleteThemeById(id);
        return ResponseEntity.noContent().build();
    }
}
