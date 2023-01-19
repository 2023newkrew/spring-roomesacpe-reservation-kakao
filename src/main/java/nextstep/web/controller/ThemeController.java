package nextstep.web.controller;

import nextstep.domain.Theme;
import nextstep.domain.dto.ThemeRequest;
import nextstep.domain.dto.ThemeResponse;
import nextstep.web.service.ThemeWebService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeWebService themeWebService;

    public ThemeController(ThemeWebService themeWebService) {
        this.themeWebService = themeWebService;
    }

    @PostMapping
    public ResponseEntity createTheme(@RequestBody ThemeRequest themeRequest){
        Theme theme = this.themeWebService.newTheme(themeRequest);

        return ResponseEntity.created(URI.create("/themes/" + theme.getId())).build();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ThemeResponse getTheme(@PathVariable long id){
        ThemeResponse themeResponse = this.themeWebService.findTheme(id);
        return themeResponse;
    }

    @GetMapping()
    @ResponseBody
    public List<ThemeResponse> getAllTheme(){
        return this.themeWebService.findAllTheme();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTheme(@PathVariable long id){
        this.themeWebService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }

}
