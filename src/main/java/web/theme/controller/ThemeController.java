package web.theme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.theme.service.ThemeService;

@RequestMapping("/theme")
@RequiredArgsConstructor
@RestController
public class ThemeController {

    private final ThemeService themeService;
}
