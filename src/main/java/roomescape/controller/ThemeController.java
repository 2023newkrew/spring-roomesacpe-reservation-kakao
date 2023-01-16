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

import static roomescape.utils.Messages.*;

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
        logger.info(CREATE_REQUEST.getMessage() + theme.toMessage());
        try {
            Theme userTheme = themeService.createTheme(theme);
            logger.info(CREATE_RESPONSE.getMessage() + userTheme.toMessage());
            return ResponseEntity.created(URI.create("/themes/" + userTheme.getId()))
                    .body(userTheme.createMessage(userTheme.getId()));
        } catch (Exception e){
            logger.error(String.valueOf(e));
            return ResponseEntity.badRequest().body(THEME_CREATE_ERROR.getMessage());
        }
    }

    @GetMapping("/themes/{id}")
    public ResponseEntity<String> lookUpTheme(@PathVariable("id") String themeId) {
        logger.info(LOOKUP_REQUEST.getMessage() + themeId);
        try {
            Theme userTheme = themeService.lookUpTheme(Long.valueOf(themeId));
            logger.info(LOOKUP_RESPONSE.getMessage() + userTheme.toMessage());
            return ResponseEntity.ok().body(userTheme.toMessage());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<String> deleteTheme(@PathVariable("id") String deleteId) {
        logger.info(DELETE_REQUEST.getMessage() + deleteId);
        try {
            themeService.deleteTheme(Long.valueOf(deleteId));
            logger.info(DELETE_RESPONSE.getMessage() + deleteId);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            logger.error(String.valueOf(e));
            return ResponseEntity.notFound().build();
        }
    }
}
