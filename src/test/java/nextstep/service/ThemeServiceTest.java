package nextstep.service;

import nextstep.domain.theme.Theme;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.dto.request.CreateThemeRequest;
import nextstep.error.ApplicationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    @InjectMocks
    private ThemeService themeService;

    @Test
    void 테마_생성에_성공한다() {
        // given
        CreateThemeRequest createThemeRequest = new CreateThemeRequest("테마 이름", "테마 설명", 22_000);
        Theme theme = new Theme(5L, createThemeRequest.getName(), createThemeRequest.getDesc(), createThemeRequest.getPrice());

        given(themeRepository.save(any(Theme.class)))
                .willReturn(theme);

        // when
        Long themeId = themeService.createTheme(createThemeRequest);

        // then
        assertThat(themeId).isEqualTo(theme.getId());
    }

    @Test
    void 존재하지_않는_이름으로_테마를_조회할_경우_예외가_발생한다() {
        // given
        String invalidName = "베니스 상인의 저택";

        given(themeRepository.findByName(eq(invalidName)))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> themeService.findThemeByName(invalidName))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    void 존재하는_이름으로_테마를_조회한다() {
        // given
        String themeName = "거울의 방";
        Theme savedTheme = new Theme(themeName, "테마 설명", 25_000);

        given(themeRepository.findByName(eq(themeName)))
                .willReturn(Optional.of(savedTheme));

        // when
        Theme findByName = themeService.findThemeByName(themeName);

        // then
        assertThat(findByName).usingRecursiveComparison()
                .isEqualTo(savedTheme);
    }

    @Test
    void 동일한_이름을_가진_테마가_존재할_경우_테마_생성에_실패한다() {
        // given
        CreateThemeRequest createThemeRequest = new CreateThemeRequest("혜화 잡화점", "중복 테마 설명", 27_000);
        Theme savedTheme = new Theme(5L, "혜화 잡화점", "테마 설명", 22_000);

        given(themeRepository.findByName(eq(savedTheme.getName())))
                .willReturn(Optional.of(savedTheme));

        // when, then
        assertThatThrownBy(() -> themeService.createTheme(createThemeRequest))
                .isInstanceOf(ApplicationException.class);
    }


}
