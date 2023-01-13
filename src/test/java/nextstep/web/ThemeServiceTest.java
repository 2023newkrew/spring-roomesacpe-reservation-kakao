package nextstep.web;

import nextstep.exception.ThemeNotFoundException;
import nextstep.exception.ThemeReservationExistsException;
import nextstep.model.Reservation;
import nextstep.service.ThemeService;
import nextstep.web.JdbcTemplateThemeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@JdbcTest
public class ThemeServiceTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private ThemeService themeService;

    @BeforeEach
    void setUp() {
        themeService = new ThemeService(new JdbcTemplateThemeRepository(jdbcTemplate));
    }

    @DisplayName("테마를 찾을 수 없으면 예외가 발생한다")
    @Test
    void getNotFoundTheme() {
        Long id = 12314L;

        Assertions.assertThatThrownBy(() -> themeService.getTheme(id))
                        .isInstanceOf(ThemeNotFoundException.class);
    }

    @DisplayName("테마 삭제시 예약이 존재하면 예외가 발생한다")
    @Test
    void deleteReservationExistsTheme() {
        Long id = 23499L;
        List<Reservation> reservations = List.of(new Reservation(null, null, null, null, null));

        Assertions.assertThatThrownBy(() -> themeService.deleteTheme(id, reservations))
                .isInstanceOf(ThemeReservationExistsException.class);
    }
}
