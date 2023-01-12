package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Theme;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcThemeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private JdbcThemeRepository jdbcThemeRepository;

    @BeforeEach
    void setUp() {
        jdbcThemeRepository = new JdbcThemeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("테마 생성 테스트")
    void createTest(){
        Theme expectedTheme = new Theme("테마이름", "테마설명", 22000);
        Theme actualTheme = jdbcThemeRepository.save(expectedTheme);

        Assertions.assertThat(actualTheme.getName()).isEqualTo(expectedTheme.getName());
        Assertions.assertThat(actualTheme.getDesc()).isEqualTo(expectedTheme.getDesc());
        Assertions.assertThat(actualTheme.getPrice()).isEqualTo(expectedTheme.getPrice());
    }
}
