package nextstep.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import nextstep.reservation.entity.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ThemeJdbcTemplateRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    private ThemeRepository repository;
    private Theme testTheme = new Theme();

    @BeforeEach
    void setUp() {
        this.repository = new ThemeJdbcTemplateRepository(jdbcTemplate, dataSource);
    }

    @Test
    void 테마를_등록하면_테마_아이디가_반환됩니다() {
        assertThat(repository.add(testTheme)).isInstanceOf(Long.class);
    }

    @Test
    void 등록된_테마를_가져올_수_있습니다() {
        Long id = repository.add(testTheme);
        assertThat(repository.findById(id).get()).isInstanceOf(Theme.class);
    }

    @Test
    void 등록된_테마를_전부_가져올_수_있습니다() {
        repository.add(new Theme());
        repository.add(new Theme());
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    void 테마를_삭제할_수_있습니다() {
        Long id = repository.add(new Theme());
        assertThat(repository.delete(id)).isTrue();
        assertThat(repository.findById(id).isEmpty()).isTrue();
    }
}