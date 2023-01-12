package kakao.domain;

import domain.ThemeFactory;
import kakao.dto.request.CreateThemeRequest;
import kakao.error.exception.DuplicatedThemeException;
import kakao.repository.ThemeJDBCRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class ThemeFactoryTest {

    @Autowired
    private ThemeJDBCRepository themeJDBCRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ThemeFactory themeFactory;

    private final CreateThemeRequest request = new CreateThemeRequest("name", "desc", 1000);

    @BeforeEach()
    void setup() {
        themeFactory = new ThemeFactory(themeJDBCRepository);
        jdbcTemplate.execute("TRUNCATE TABLE theme");
        jdbcTemplate.execute("ALTER TABLE theme ALTER COLUMN id RESTART WITH 1");
    }

    @DisplayName("CreateThemeRequest를 받아 Theme을 생성한다")
    @Test
    void createTheme() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> themeFactory.create(request));
    }

    @DisplayName("이미 존재하는 name의 CreateThemeRequest의 경우 DuplicateTheme 예외를 발생한다")
    @Test
    void createDuplicateNamedTheme() {
        themeJDBCRepository.save(themeFactory.create(request));

        Assertions.assertThatExceptionOfType(DuplicatedThemeException.class)
                .isThrownBy(() -> themeFactory.create(request));
    }
}
