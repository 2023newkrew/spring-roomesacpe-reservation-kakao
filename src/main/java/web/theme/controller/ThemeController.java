package web.theme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.theme.dto.ThemeRequestDto;
import web.theme.service.ThemeService;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/themes")
@RequiredArgsConstructor
@RestController
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<Void> reservation(@RequestBody @Valid ThemeRequestDto requestDto) {

        long createdId = themeService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/themes/" + createdId))
                .build();
    }
}
