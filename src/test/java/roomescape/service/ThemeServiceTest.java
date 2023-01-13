package roomescape.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.controller.dto.ThemeRequest;
import roomescape.controller.dto.ThemeResponse;
import roomescape.domain.Theme;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ThemeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class ThemeServiceTest {

    @Autowired
    ThemeService themeService;

    @Autowired
    ThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        themeRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        themeRepository.deleteAll();
    }

    @DisplayName("새로운 테마를 생성할 수 있다.")
    @Test
    void create() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("테마1", "설명1", 1000);

        // when
        Long themeId = themeService.createTheme(themeRequest);

        // then
        Theme theme = themeRepository.findThemeById(themeId)
                .orElseThrow(IllegalArgumentException::new);
        assertThat(theme.getName()).isEqualTo(themeRequest.getName());
        assertThat(theme.getDesc()).isEqualTo(themeRequest.getDesc());
        assertThat(theme.getPrice()).isEqualTo(themeRequest.getPrice());
    }

    @DisplayName("새로운 테마 생성 시, 기존 테마와 이름이 겹치면 예외가 발생한다.")
    @Test
    void createNameException() {
        // given
        String duplicatedName = "테마1";
        Theme theme1 = new Theme(duplicatedName, "설명1", 1000);
        themeRepository.insertTheme(theme1);
        ThemeRequest themeRequest = new ThemeRequest(duplicatedName, "다른설명1", 3000);

        // when & then
        assertThatCode(() -> themeService.createTheme(themeRequest))
                .isInstanceOf(RoomEscapeException.class);
    }

    @DisplayName("저장된 모든 테마를 조회할 수 있다.")
    @Test
    void getAllThemes() {
        // given
        Theme theme1 = new Theme("테마1", "설명1", 1000);
        Theme theme2 = new Theme("테마2", "설명2", 2000);
        Theme theme3 = new Theme("테마3", "설명3", 3000);
        themeRepository.insertTheme(theme1);
        themeRepository.insertTheme(theme2);
        themeRepository.insertTheme(theme3);

        // when
        List<ThemeResponse> allThemes = themeService.getAllThemes();

        // then
        assertThat(allThemes).hasSize(3);
    }

    @DisplayName("id를 통해 특정 테마를 조회할 수 있다.")
    @Test
    void getTheme() {
        // given
        Theme theme = new Theme("테마1", "설명1", 1000);
        Long themeId = themeRepository.insertTheme(theme);

        // when
        ThemeResponse themeResponse = themeService.getTheme(themeId);

        // then
        assertThat(themeResponse.getName()).isEqualTo(theme.getName());
        assertThat(themeResponse.getDesc()).isEqualTo(theme.getDesc());
        assertThat(themeResponse.getPrice()).isEqualTo(theme.getPrice());
    }

    @DisplayName("id에 대응되는 테마가 없다면 예외가 발생한다.")
    @Test
    void getThemeException() {
        // given
        Long invalidThemeId = 1000000L;

        // when & then
        assertThatCode(() -> themeService.getTheme(invalidThemeId))
                .isInstanceOf(RoomEscapeException.class);
    }

    @DisplayName("id와 ThemeRequest를 통해 id에 대응되는 테마의 정보를 바꿀 수 있다.")
    @Test
    void changeTheme() {
        // given
        Theme theme = new Theme("테마1", "설명1", 1000);
        Long themeId = themeRepository.insertTheme(theme);
        ThemeRequest themeRequest = new ThemeRequest("바뀐테마1", "바뀐설명1", 5000);

        // when
        themeService.changeTheme(themeId, themeRequest);

        // then
        Theme changedTheme = themeRepository.findThemeById(themeId)
                .orElseThrow(IllegalArgumentException::new);
        assertThat(changedTheme.getName()).isEqualTo(themeRequest.getName());
        assertThat(changedTheme.getDesc()).isEqualTo(themeRequest.getDesc());
        assertThat(changedTheme.getPrice()).isEqualTo(themeRequest.getPrice());
    }

    @DisplayName("id에 대응되는 테마가 없다면 테마의 정보를 바꿀 수 없다.")
    @Test
    void changeThemeException() {
        // given
        Long invalidThemeId = 1000000L;
        ThemeRequest themeRequest = new ThemeRequest("바뀐테마1", "바뀐설명1", 5000);

        // when & then
        assertThatCode(() -> themeService.changeTheme(invalidThemeId, themeRequest))
                .isInstanceOf(RoomEscapeException.class);
    }

    @DisplayName("id에 대응되는 테마를 지울 수 있다")
    @Test
    void deleteTheme() {
        // given
        Theme theme = new Theme("테마1", "설명1", 1000);
        Long themeId = themeRepository.insertTheme(theme);

        // when
        themeService.deleteTheme(themeId);

        // then
        Optional<Theme> optionalTheme = themeRepository.findThemeById(themeId);
        assertThat(optionalTheme).isEmpty();
    }

    @DisplayName("id에 대응되는 테마가 없다면 삭제할 수 없다.")
    @Test
    void deleteThemeException() {
        // given
        Long invalidThemeId = 1000000L;

        // when & then
        assertThatCode(() -> themeService.deleteTheme(invalidThemeId))
                .isInstanceOf(RoomEscapeException.class);
    }
}