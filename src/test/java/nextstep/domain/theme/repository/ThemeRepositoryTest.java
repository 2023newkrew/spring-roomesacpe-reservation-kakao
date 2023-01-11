package nextstep.domain.theme.repository;

import nextstep.common.DatabaseExecutor;
import nextstep.domain.theme.Theme;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static nextstep.domain.QuerySetting.Theme.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest(includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { DatabaseExecutor.class })
    }
)
public class ThemeRepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;

    @BeforeAll
    static void createTable(@Autowired DatabaseExecutor databaseExecutor) {
        databaseExecutor.createThemeTable();
    }

    @Test
    void 테마를_생성한다() {
        // given
        Theme theme = new Theme("혜화 잡화점", "테마 설명", 22_000);

        // when
        Theme savedTheme = themeRepository.save(theme);

        // then
        assertThat(savedTheme.getId()).isNotNull();
        assertThat(savedTheme).usingRecursiveComparison()
                .ignoringFields(PK_NAME)
                .isEqualTo(theme);
    }

    @Test
    void 테마를_이름으로_조회한다() {
        // given
        String themeName = "혜화 잡화점";
        Theme savedTheme = themeRepository.save(new Theme(themeName, "테마 설명", 22_000));

        // when
        Theme findByName = themeRepository.findByName(themeName)
                .orElseThrow();

        // then
        assertThat(findByName).usingRecursiveComparison()
                .isEqualTo(savedTheme);
    }

    @Test
    void 특정_이름을_가진_테마가_존재하지_않을_경우_아무것도_반환되지_않는다() {
        // given
        String themeName = "혜화 잡화점";

        // when, then
        assertThat(themeRepository.findByName(themeName)).isEqualTo(Optional.empty());
    }

}
