package nextstep.theme.repository;

import nextstep.theme.domain.Theme;
import nextstep.theme.repository.jdbc.ThemeResultSetParser;
import nextstep.theme.repository.jdbc.ThemeStatementCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

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
}