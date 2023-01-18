package nextstep.roomescape.theme;

import nextstep.roomescape.repository.model.Theme;
import nextstep.roomescape.repository.ThemeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


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
        Theme findTheme = themeRepository.findById(id);
        assertEquals("워너고홈", findTheme.getName());
        assertEquals("병맛 어드벤처 회사 코믹물", findTheme.getDesc());
        assertEquals(29000, findTheme.getPrice());

    }

    @Test
    void findByIdNothing() {
        Theme findTheme = themeRepository.findById(0L);
        assertNull(findTheme);

    }

    @Test
    @Transactional
    void delete() {
        Theme theme = new Theme(null,"워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Long id = themeRepository.create(theme);
        themeRepository.delete(id);
        Theme findTheme = themeRepository.findById(id);
        assertNull(findTheme);
    }

    @Test
    @Transactional
    void updateById() {
        Theme theme = new Theme(null,"워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        Long id = themeRepository.create(theme);
        themeRepository.updateById(id, new Theme(null,"워너고홈", "병맛 어드벤처 회사 코믹물", 28000));
        Theme findTheme = themeRepository.findById(id);
        assertEquals("워너고홈", findTheme.getName());
        assertEquals("병맛 어드벤처 회사 코믹물", findTheme.getDesc());
        assertEquals(28000, findTheme.getPrice());

    }
}