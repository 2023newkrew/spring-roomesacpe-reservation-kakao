package nextstep.theme.controller;

import lombok.RequiredArgsConstructor;
import nextstep.theme.dto.ThemeDetail;
import nextstep.theme.dto.ThemeDto;
import nextstep.theme.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    @PostMapping
    ResponseEntity<ThemeDetail> createTheme(@RequestBody ThemeDto themeDto){
        Long themeId = themeService.save(themeDto);
        return ResponseEntity.created(URI.create("/themes/" + themeId)).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<ThemeDetail> getTheme(@PathVariable Long id){
        ThemeDetail themeDetail = themeService.findById(id);
        return ResponseEntity.ok().body(themeDetail);
    }

    @GetMapping
    ResponseEntity<List<ThemeDetail>> getThemes(){
        List<ThemeDetail> themeDetails = themeService.findAll();
        return ResponseEntity.ok().body(themeDetails);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteTheme(@PathVariable Long id){
        themeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
