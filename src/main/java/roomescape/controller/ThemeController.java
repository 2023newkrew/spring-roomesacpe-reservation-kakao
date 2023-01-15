package roomescape.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Theme;
import roomescape.service.Theme.ThemeService;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ThemeController {
    final ThemeService themeService;
    private static final Logger logger =
            LoggerFactory.getLogger(ThemeController.class);

    @Autowired
    public ThemeController(@Qualifier("WebTheme")ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/themes")
    public ResponseEntity<String> createTheme(@RequestBody @Valid Theme theme){
        logger.info("Request Create Theme - " + theme.toMessage());
        Theme userTheme = themeService.createTheme(theme);
        logger.info("Finish Create Theme - " + userTheme.toMessage());
        return ResponseEntity.created(URI.create("/themes/" + userTheme.getId()))
                .body(userTheme.createMessage(userTheme.getId()));
    }

    @GetMapping("/themes/{id}")
    public ResponseEntity<String> lookUpTheme(@PathVariable("id") String themeId) {
        logger.info("Request lookUp Theme, Id: " + themeId);
        Theme userTheme = themeService.lookUpTheme(Long.valueOf(themeId));
        logger.info("Finish lookUp Theme " + userTheme.toMessage());
        return ResponseEntity.ok().body(userTheme.toMessage());
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<String> deleteTheme(@PathVariable("id") String deleteId) {
        logger.info("Request delete Theme, Id: " + deleteId);
        themeService.deleteTheme(Long.valueOf(deleteId));
        logger.info("Finish delete Theme Id: " + deleteId);
        return ResponseEntity.noContent().build();
    }
}
