package nextstep.web;

import nextstep.model.Theme;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class JdbcTemplateThemeRepositoryTest {

    @Autowired
    private JdbcTemplateThemeRepository repository;

    @DisplayName("테마를 저장한다")
    @Test
    void save() {
        Theme theme = new Theme(null, "테마 이름", "테마 설명 내용", 32000);

        Theme savedTheme = repository.save(theme);

        assertThat(savedTheme.getId()).isNotNull();
        assertThat(savedTheme.getName()).isEqualTo(theme.getName());
        assertThat(savedTheme.getDesc()).isEqualTo(theme.getDesc());
        assertThat(savedTheme.getPrice()).isEqualTo(theme.getPrice());
    }

    @DisplayName("id로 테마를 조회한다")
    @Test
    void findById() {
        Theme theme = repository.save(new Theme(null, "테마 이름", "테마 설명 내용", 32000));

        Theme found = repository.findById(theme.getId()).orElseThrow();

        assertThat(found).isEqualTo(theme);
    }

    @DisplayName("이름으로 테마를 조회한다")
    @Test
    void findByName() {
        Theme theme = repository.save(new Theme(null, "테마 이름", "테마 설명 내용", 32000));

        Theme found = repository.findByName(theme.getName()).orElseThrow();

        assertThat(found).isEqualTo(theme);
    }

    @DisplayName("존재하지 않는 id로 테마를 조회한다")
    @Test
    void findById_Empty() {
        Long id = 12314L;

        Optional<Theme> found = repository.findById(id);

        assertTrue(found.isEmpty());
    }

    @DisplayName("테마를 삭제한다")
    @Test
    void deleteById() {
        Theme theme = repository.save(new Theme(null, "테마 이름", "테마 설명 내용", 32000));

        repository.deleteById(theme.getId());

        assertTrue(repository.findById(theme.getId()).isEmpty());
    }
}