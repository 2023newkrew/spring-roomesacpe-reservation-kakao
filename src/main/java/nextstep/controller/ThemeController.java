package nextstep.controller;

import nextstep.domain.Theme;
import nextstep.dto.ReservationResponse;
import nextstep.dto.ThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity createTheme(@RequestBody ThemeRequest themeRequest){
        Theme theme = this.themeService.newTheme(themeRequest);

        return ResponseEntity.created(URI.create("/themes/" + theme.getId())).build();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ThemeResponse getTheme(@PathVariable long id){
        ThemeResponse themeResponse = this.themeService.findTheme(id);
        return themeResponse;
    }

    @GetMapping()
    @ResponseBody
    public List<ThemeResponse> getAllTheme(){
        return this.themeService.findAllTheme();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTheme(@PathVariable long id){
        this.themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }

}
