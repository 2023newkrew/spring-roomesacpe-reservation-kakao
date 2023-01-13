package nextstep.web;

import java.net.URI;
import nextstep.web.dto.ThemeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThemeController {

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest request) {
        return ResponseEntity.created(URI.create("/themes/1")).build();
    }
}
