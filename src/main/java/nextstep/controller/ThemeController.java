package nextstep.controller;

import java.net.URI;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.dto.ThemeResponseDto;
import nextstep.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/themes")
@Controller
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping("")
    ResponseEntity createTheme(@RequestBody ThemeCreateDto themeCreateDto) {
        return ResponseEntity.created(
                URI.create(String.format("/themes/%d", themeService.createTheme(themeCreateDto)))).build();
    }

    @GetMapping("{id}")
    ResponseEntity<ThemeResponseDto> findTheme(@PathVariable Long id) {
        return ResponseEntity.ok(themeService.findTheme(id));
    }

    @PutMapping("")
    ResponseEntity editTheme(@RequestBody ThemeEditDto themeEditDto) {
        boolean success = themeService.editTheme(themeEditDto);
        if(success){
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity deleteTheme(@PathVariable Long id){
        themeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }





}
