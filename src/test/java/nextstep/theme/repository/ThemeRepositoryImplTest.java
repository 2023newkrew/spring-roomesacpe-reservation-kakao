package nextstep.theme.repository;

import nextstep.theme.domain.Theme;
import nextstep.theme.repository.jdbc.ThemeResultSetParser;
import nextstep.theme.repository.jdbc.ThemeStatementCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

@SqlGroup(
        {
                @Sql("classpath:/dropTable.sql"),
                @Sql("classpath:/schema.sql")
        })
@JdbcTest
class ThemeRepositoryImplTest {

    final JdbcTemplate jdbcTemplate;

    final ThemeStatementCreator statementCreator;

    final ThemeResultSetParser resultSetParser;

    final ThemeRepository repository;


    @Autowired
    public ThemeRepositoryImplTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.statementCreator = new ThemeStatementCreator();
        this.resultSetParser = new ThemeResultSetParser();
        this.repository = new ThemeRepositoryImpl(jdbcTemplate, statementCreator, resultSetParser);
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class insert {

        @DisplayName("호출 횟수만큼 ID가 증가하는지 확인")
        @Test
        void should_increaseId_when_insertTwice() {
            var theme = new Theme(null, "name", "desc", 1000);

            Long id1 = repository.insert(theme)
                    .getId();
            Long id2 = repository.insert(theme)
                    .getId();

            Assertions.assertThat(id1 + 1L)
                    .isEqualTo(id2);
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class getById {

        @BeforeEach
        void setUp() {
            List<Theme> themes = List.of(
                    new Theme(null, "theme1", "theme1", 1000),
                    new Theme(null, "theme2", "theme2", 2000),
                    new Theme(null, "theme3", "theme3", 3000)
            );
            insertTestThemes(themes);
        }

        @DisplayName("ID와 일치하는 예약 확인")
        @ParameterizedTest
        @MethodSource
        void should_returnTheme_when_givenId(Long id, Theme theme) {
            Theme actual = repository.getById(id);

            Assertions.assertThat(actual)
                    .extracting(
                            Theme::getId,
                            Theme::getName,
                            Theme::getDesc,
                            Theme::getPrice
                    )
                    .contains(
                            id,
                            theme.getName(),
                            theme.getDesc(),
                            theme.getPrice()
                    );
        }


        List<Arguments> should_returnTheme_when_givenId() {
            return List.of(
                    Arguments.of(1L, new Theme(null, "theme1", "theme1", 1000)),
                    Arguments.of(2L, new Theme(null, "theme2", "theme2", 2000)),
                    Arguments.of(3L, new Theme(null, "theme3", "theme3", 3000))
            );
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class getAll {
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class update {
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class delete {
    }

    private void insertTestThemes(List<Theme> themes) {
        themes.forEach(theme -> {
            jdbcTemplate.update(connection -> statementCreator.createInsert(connection, theme));
        });
    }
}