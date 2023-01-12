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

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    @Test
    void 테마_목록이_n개_이상일_경우_n개씩_조회한다() {
        // given
        int page = 0, size = 5;
        Stream.of("혜화 잡화점", "거울의 방", "베니스 상인의 저택", "워너고홈", "명당", "스위트홈", "오즈의 마법사")
                .map(themeName -> new Theme(themeName, "테마 설명", 22_000))
                .forEach(themeRepository::save);

        // when
        List<Theme> themes = themeRepository.findAll(page, size);

        // then
        assertThat(themes.size()).isEqualTo(size);
        themes.forEach(theme -> assertThat(theme).isNotNull());
    }

    @Test
    void 테마_목록이_n개_미만일_경우_모두_조회한다() {
        // given
        int page = 0, size = 5;
        Stream.of("혜화 잡화점", "거울의 방", "베니스 상인의 저택")
                .map(themeName -> new Theme(themeName, "테마 설명", 22_000))
                .forEach(themeRepository::save);

        // when
        List<Theme> themes = themeRepository.findAll(page, size);

        // then
        assertThat(themes.size()).isLessThan(size);
        themes.forEach(theme -> assertThat(theme).isNotNull());
    }

    @Test
    void 테마_아이디로_테마를_삭제한다() {
        // given
        Theme savedTheme = themeRepository.save(new Theme("테마 제목", "테마 설명", 22_000));

        // when
        themeRepository.deleteById(savedTheme.getId());

        // then
        assertThat(themeRepository.findByName(savedTheme.getName())).isEqualTo(Optional.empty());
    }

    @Test
    void 테마_이름를_수정한다() {
        // given
        Theme savedTheme = themeRepository.save(new Theme("이전 테마", "테마 설명", 22_000));
        String newName = "새로운 테마 제목";

        // when
        themeRepository.update(new Theme(savedTheme.getId(), newName, "테마 설명", 22_000));

        // then
        assertThat(themeRepository.findByName(savedTheme.getName())).isEqualTo(Optional.empty());
        assertThat(themeRepository.findByName(newName)).isNotNull();
    }


    @Test
    void 테마_설명과_가격을_수정한다() {
        // given
        Theme savedTheme = themeRepository.save(new Theme("테마 제목", "테마 설명", 22_000));
        String newDesc = "새로운 테마 설명";
        int newPrice = savedTheme.getPrice() + 10000;

        // when
        themeRepository.update(new Theme(savedTheme.getId(), savedTheme.getName(), newDesc, newPrice));

        // then
        Theme theme = themeRepository.findByName(savedTheme.getName())
                .orElseThrow();
        assertThat(theme.getDesc()).isEqualTo(newDesc);
        assertThat(theme.getPrice()).isEqualTo(newPrice);
    }
}
