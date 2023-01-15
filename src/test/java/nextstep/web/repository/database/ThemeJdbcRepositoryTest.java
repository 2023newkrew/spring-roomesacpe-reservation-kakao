package nextstep.web.repository.database;

import nextstep.domain.Theme;
import nextstep.web.exception.BusinessException;
import nextstep.web.repository.ThemeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ThemeJdbcRepositoryTest {

    ThemeRepository themeRepository;

    Theme theme;

    @Autowired
    public ThemeJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.themeRepository = new ThemeJdbcRepository(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    }

    @Test
    void 예약을_저장후_조회할_수_있다() {
        Long createdId = themeRepository.save(theme);

        Assertions.assertThat(themeRepository.findById(createdId)
                        .getId())
                .isEqualTo(createdId);
        Assertions.assertThat(themeRepository.findById(createdId)
                        .getName())
                .isEqualTo(theme.getName());
    }

    @Test
    void 예약후_취소할_수_있다() {
        Long createdId = themeRepository.save(theme);
        themeRepository.deleteById(createdId);

        Assertions.assertThatThrownBy(() -> themeRepository.findById(createdId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 없는_예약을_취소하면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> themeRepository.deleteById(1321L))
                .isInstanceOf(BusinessException.class);
    }
}
