package roomescape.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.model.Theme;

import java.util.Optional;

@SpringBootTest
public class ThemeJdbcRepositoryTest {
    @Autowired
    private ThemeJdbcRepository themeRepository;

    @DisplayName("Theme 저장 후 데이터베이스에 존재함을 확인")
    @Test
    @Transactional
    public void saveAndFindByIdTest() {
        //given
        Theme theme = new Theme(null, "Test Theme", "lorem ipsum", 1000);
        //when
        long themeId = themeRepository.save(theme);
        Optional<Theme> optionalTheme = themeRepository.findOneById(themeId);
        //then
        Assertions.assertThat(optionalTheme).isNotEmpty();
    }

    @DisplayName("Theme 저장, 삭제 후 데이터베이스에 존재하지 않음을 확인")
    @Test
    @Transactional
    public void deleteAndFindByIdTest() {
        //given
        Theme theme = new Theme(null, "Test Theme", "lorem ipsum", 1000);
        //when
        long themeId = themeRepository.save(theme);
        themeRepository.delete(themeId);
        Optional<Theme> optionalTheme = themeRepository.findOneById(themeId);
        //then
        Assertions.assertThat(optionalTheme).isEmpty();
    }

    @DisplayName("중복되는 Theme 이름 존재 유무 확인")
    @Test
    @Transactional
    public void hasThemeWithNameTest() {
        //given
        Theme theme = new Theme(null, "Test Theme", "lorem ipsum", 1000);
        //when
        long themeId = themeRepository.save(theme);
        //then
        Assertions.assertThat(themeRepository.hasThemeWithName("Test Theme")).isTrue();
    }
}
