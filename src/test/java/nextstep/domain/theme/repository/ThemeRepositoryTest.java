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
        databaseExecutor.createTable(TABLE_NAME);
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

}
