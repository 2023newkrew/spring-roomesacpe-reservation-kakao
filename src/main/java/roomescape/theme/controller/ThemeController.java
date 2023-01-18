package roomescape.theme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.theme.dao.ThemeDao;
import roomescape.theme.domain.Theme;
import roomescape.theme.dto.ThemeDto;

import java.net.URI;

@RestController
public class ThemeController {
    @Autowired
    private ThemeDao themeDao;

    @PostMapping("/themes")
    public ResponseEntity createTheme(@RequestBody ThemeDto ThemeDto) {//테마를 생성한다 201
        Long id = themeDao.addTheme(new Theme(ThemeDto)).getId();
        return ResponseEntity.created(URI.create("/themes/"+id)).build();
    }

//    @GetMapping("/themes")
//    public ResponseEntity showAllThemes() { //모든 테마를 조회한다 200
//        List<Theme> themes = themeDao.findAllTheme();
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("themes/{id}")
//    public ResponseEntity deleteTheme(@PathVariable Long id) { //테마를 삭제한다
//        themeDao.removeTheme(id);
//        return ResponseEntity.noContent().build();
//    }

}
