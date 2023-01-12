package nextstep.web.theme.controller;

import lombok.RequiredArgsConstructor;
import nextstep.web.common.controller.Response;
import nextstep.web.theme.dto.*;
import nextstep.web.theme.service.ThemeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public Response<CreateThemeResponseDto> createTheme(@RequestBody @Valid CreateThemeRequestDto requestDto) {
        return new Response<>(HttpStatus.OK.value(), HttpStatus.CREATED.name(), themeService.createTheme(requestDto));
    }

    @GetMapping("{id}")
    public Response<FindThemeResponseDto> findTheme(@PathVariable Long id) {
        FindThemeResponseDto theme = themeService.findTheme(id);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), theme);
    }

    @GetMapping
    public Response<FindAllThemeResponseDto> findAllTheme() {
        FindAllThemeResponseDto theme = themeService.findAllTheme();

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), theme);
    }

    @DeleteMapping("{id}")
    public Response<Void> deleteTheme(@PathVariable @Min(value = 1, message = "ID를 확인해 주세요") Long id) {
        themeService.deleteTheme(id);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.NO_CONTENT.name(), null);
    }

    @PutMapping("{id}")
    public Response<Void> updateTheme(@PathVariable @Min(value = 1, message = "ID를 확인해 주세요") Long id,
                                      @RequestBody @Valid CreateThemeRequestDto requestDto) {
        themeService.updateTheme(requestDto, id);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), null);
    }
}
