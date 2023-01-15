package nextstep.service;

import nextstep.exception.ThemeReservedException;
import nextstep.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

class ThemeServiceTest {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final ThemeRepository themeRepository = new ThemeH2JdbcTemplateRepository(jdbcTemplate) {
        @Override
        public void deleteById(Long id) {}
    };

    @Test
    void 예약되지_않은_테마는_삭제_가능() {
        ReservationRepository reservationRepository = new ReservationH2JdbcTemplateRepository(jdbcTemplate) {
            @Override
            public boolean existsByThemeId(Long themeId) { return false; }
        };
        ThemeService themeService = new ThemeService(themeRepository, reservationRepository);
        Assertions.assertDoesNotThrow(() -> themeService.deleteById(1L));
    }

    @Test
    void 예약되어_있는_테마는_삭제_불가능() {
        ReservationRepository reservationRepository = new ReservationMemoryRepository() {
            @Override
            public boolean existsByThemeId(Long themeId) { return true; }
        };
        ThemeService themeService = new ThemeService(themeRepository, reservationRepository);
        Assertions.assertThrows(ThemeReservedException.class,
                () -> themeService.deleteById(1L));
    }

}