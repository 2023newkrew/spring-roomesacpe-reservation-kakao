package nextstep.roomescape.theme;

import nextstep.roomescape.theme.domain.entity.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThemeRepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;


//    @Test
//    @Transactional
//    void create() {
//        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
//        Theme createTheme = themeRepository.create(theme);
//
//        assertTrue(theme.getName().equals(createTheme.getName()) &&
//                theme.getDesc().equals(createTheme.getDesc()) &&
//                theme.getPrice().equals(createTheme.getPrice()));
//    }

    @Test
    void findAll() {
    }

    @Test
    void findByName() {
    }

    @Test
    void delete() {
    }
}