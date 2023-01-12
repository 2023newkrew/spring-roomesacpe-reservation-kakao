package nextstep.web.theme.controller;

import lombok.RequiredArgsConstructor;
import nextstep.web.common.controller.Response;
import nextstep.web.theme.dto.*;
import nextstep.web.theme.service.ThemeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/themes")
public class ThemeController {

    private static final String BASE_URI = "/theme";

    private final ThemeService themeService;

    @PostMapping
    public Response<CreateThemeResponseDto> createTheme(@RequestBody @Valid CreateThemeRequestDto requestDto) {
        CreateThemeResponseDto location = new CreateThemeResponseDto(
                BASE_URI + "/" + themeService.createTheme(requestDto)
        );

        return new Response<>(HttpStatus.OK.value(), HttpStatus.CREATED.name(), location);
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
    public Response<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.NO_CONTENT.name(), null);
    }

    @PutMapping("{id}")
    public Response<Void> updateTheme(@PathVariable Long id, @RequestBody @Valid CreateThemeRequestDto requestDto) {
        themeService.updateTheme(requestDto, id);

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), null);
    }
}
