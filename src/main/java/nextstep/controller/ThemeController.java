package nextstep.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.dto.ThemeResponseDto;
import nextstep.exception.ConflictException;
import nextstep.service.ReservationService;
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
    private final ReservationService reservationService;

    @PostMapping("")
    ResponseEntity createTheme(@RequestBody ThemeCreateDto themeCreateDto) {
        return ResponseEntity.created(
                URI.create(String.format("/themes/%d", themeService.createTheme(themeCreateDto).getId()))).build();
    }

    @GetMapping("{id}")
    ResponseEntity<ThemeResponseDto> findTheme(@PathVariable Long id) {
        return ResponseEntity.ok(ThemeResponseDto.of(themeService.findById(id)));
    }

    @PutMapping("")
    ResponseEntity editTheme(@RequestBody ThemeEditDto themeEditDto) {
        boolean exist = reservationService.existByThemeId(themeEditDto.getId());
        if(exist){
            throw new ConflictException("해당하는 테마를 예약한 사람이 존재합니다.");
        }
        boolean success = themeService.editTheme(themeEditDto);
        if(success){
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity deleteTheme(@PathVariable Long id){
        boolean exist = reservationService.existByThemeId(id);
        if(exist){
            throw new ConflictException("해당하는 테마를 예약한 사람이 존재합니다.");
        }
        themeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }





}
