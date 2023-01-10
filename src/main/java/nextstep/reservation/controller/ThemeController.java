package nextstep.reservation.controller;

import nextstep.reservation.dto.ThemeRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    @PostMapping
    public ResponseEntity<Object> addTheme(@RequestBody ThemeRequestDto themeRequestDto) {
        return null;
//        return ResponseEntity.status(HttpStatus.CREATED).header("Location","");
    }
}
