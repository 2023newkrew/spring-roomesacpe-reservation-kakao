package nextstep.repository.theme;

import nextstep.domain.theme.Theme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("Theme Repository 테스트 (콘솔)")
@Sql(scripts = {"classpath:recreate.sql"})
public class WebAppThemeRepositoryTest {

    private final ThemeRepository webAppThemeRepository;

    @Autowired
    public WebAppThemeRepositoryTest(WebAppThemeRepository webAppThemeRepository) {
        this.webAppThemeRepository = webAppThemeRepository;
    }

    private static final Theme newTheme = new Theme(
            null,
            "작은 악마들",
            "잠실 인근에서 발생한 사건 수사",
            30000
    );

    @DisplayName("예외 없이 정상적으로 테마 생성 및 추가")
    @Test
    void addTheme() {
        assertThatNoException().isThrownBy(() -> webAppThemeRepository.add(newTheme));
    }

    @DisplayName("3개의 테마 추가 후 전체 조회시 개수 일치 확인 (기존 1개 + 3개 추가)")
    @Test
    void findAllTheme() {
        // given
        webAppThemeRepository.add(newTheme);
        webAppThemeRepository.add(newTheme);
        webAppThemeRepository.add(newTheme);

        // when
        List<Theme> themeList = webAppThemeRepository.findAll();

        // then
        assertThat(themeList.size()).isEqualTo(4);
    }

    @DisplayName("예외 없이 정상적으로 테마 업데이트 확인")
    @Test
    void updateTheme() {
        webAppThemeRepository.add(newTheme);

        Theme updatedTheme = new Theme(
                1l,
                "작은 악마들2",
                "잠실 롯데 타워 인근에서 발생한 사건 수사",
                32000
        );

        assertThatNoException().isThrownBy(() -> webAppThemeRepository.update(updatedTheme));
    }

    @DisplayName("예외 없이 정상적으로 테마 삭제 확인")
    @Test
    void deleteTheme() {
        long id = webAppThemeRepository.add(newTheme);
        assertThatNoException().isThrownBy(() -> webAppThemeRepository.delete(id));
    }
}
