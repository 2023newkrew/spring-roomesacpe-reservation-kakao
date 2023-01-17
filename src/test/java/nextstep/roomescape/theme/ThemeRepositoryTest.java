package nextstep.roomescape.theme;

import nextstep.roomescape.theme.repository.model.Theme;
import nextstep.roomescape.theme.repository.ThemeRepository;
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
        Long id = themeRepository.create(theme);

        assertNotNull(id);
    }

    @Test
    @Transactional
    void findById() {
        Theme theme = new Theme(null,"워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Long id = themeRepository.create(theme);
        Theme findTheme = themeRepository.findById(id).get();
        assertEquals("워너고홈", findTheme.getName());
        assertEquals("병맛 어드벤처 회사 코믹물", findTheme.getDesc());
        assertEquals(29000, findTheme.getPrice());

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
        Long id = themeRepository.create(theme);
        themeRepository.delete(id);
        Optional<Theme> findTheme = themeRepository.findById(id);
        assertTrue(findTheme.isEmpty());
    }

    @Test
    @Transactional
    void updateById() {
        Theme theme = new Theme(null,"워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Long id = themeRepository.create(theme);
        themeRepository.updateById(id, new Theme(null,"워너고홈", "병맛 어드벤처 회사 코믹물", 28000));
        Theme findTheme = themeRepository.findById(id).get();
        assertEquals("워너고홈", findTheme.getName());
        assertEquals("병맛 어드벤처 회사 코믹물", findTheme.getDesc());
        assertEquals(28000, findTheme.getPrice());

    }
}