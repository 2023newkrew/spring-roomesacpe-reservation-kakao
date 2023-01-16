package nextstep.roomescape.theme;

import nextstep.roomescape.theme.domain.entity.Theme;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThemeRepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;


    @Test
    @Transactional
    void create() {
        Theme theme = new Theme(null,"워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Theme createTheme = themeRepository.create(theme);

        assertTrue(theme.getName().equals(createTheme.getName()) &&
                theme.getDesc().equals(createTheme.getDesc()) &&
                theme.getPrice().equals(createTheme.getPrice()));
    }

//    @Test
//    @Transactional
//    void findAll() {
//        Theme theme = new Theme(null,"워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
//        Theme createTheme = themeRepository.create(theme);
//    }

    @Test
    @Transactional
    void findById() {
        Theme theme = new Theme(null,"워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Theme createTheme = themeRepository.create(theme);
        Theme findTheme = themeRepository.findById(createTheme.getId()).get();
        assertEquals(createTheme.getName(), findTheme.getName());
        assertEquals(createTheme.getDesc(), findTheme.getDesc());
        assertEquals(createTheme.getPrice(), findTheme.getPrice());

    }

    @Test
    void findByIdNothing() {
        Optional<Theme> findTheme = themeRepository.findById(0L);
        assertTrue(findTheme.isEmpty());

    }

    @Test
    @Transactional
    void delete() {
        Theme theme = new Theme(null,"워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Theme createTheme = themeRepository.create(theme);
        themeRepository.delete(createTheme.getId());
        Optional<Theme> findTheme = themeRepository.findById(createTheme.getId());
        assertTrue(findTheme.isEmpty());
    }
}