package nextstep.domain;

import nextstep.domain.repository.ReservationRepository;
import nextstep.domain.repository.SimpleReservationRepository;
import nextstep.domain.repository.SimpleThemeRepository;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.dto.ThemesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ThemeManagerTest {
    private ReservationRepository reservationRepository;
    private ThemeRepository themeRepository;

    private ThemeManager themeManager;

    @BeforeEach
    void setUp() {
        reservationRepository = new SimpleReservationRepository();
        themeRepository = new SimpleThemeRepository();
        themeManager = new ThemeManager(reservationRepository, themeRepository);
    }

    @DisplayName("테마를 생성한다.")
    @Test
    void createTheme() {
        // given
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마이름", "훌륭한 테마입니다.", 1000000);

        // when
        Long id = themeManager.createTheme(themeRequest);

        // then
        assertThat(id).isEqualTo(1);
    }

    @DisplayName("id로 테마를 찾는다")
    @Test
    void findThemeById() {
        // given
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마이름", "훌륭한 테마입니다.", 1000000);
        Long id = themeManager.createTheme(themeRequest);

        // when
        ThemeResponse themeResponse =  themeManager.findThemeById(id);

        // then
        assertThat(themeResponse).isEqualTo(
                new ThemeResponse(id, themeRequest.getName(), themeRequest.getDesc(), themeRequest.getPrice()));
    }

    @DisplayName("모든 테마 목록을 찾는다")
    @Test
    void getAllThemes() {
        // given
        themeManager.createTheme(new CreateThemeRequest("테마이름1", "훌륭한 테마입니다.", 1000000));
        themeManager.createTheme(new CreateThemeRequest("테마이름2", "훌륭한 테마입니다.", 1000000));
        themeManager.createTheme(new CreateThemeRequest("테마이름3", "훌륭한 테마입니다.", 1000000));
        themeManager.createTheme(new CreateThemeRequest("테마이름4", "훌륭한 테마입니다.", 1000000));

        // when
        ThemesResponse themes = themeManager.getAllThemes();

        // then
        assertThat(themes.getThemes().size()).isEqualTo(4);
    }

    @DisplayName("id로 테마를 삭제한다")
    @Test
    void deleteThemeById() {
        // given
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마이름", "훌륭한 테마입니다.", 1000000);
        Long id = themeManager.createTheme(themeRequest);

        // when
        boolean deleted = themeManager.deleteThemeById(id);

        // then
        assertThat(deleted).isTrue();
    }
}