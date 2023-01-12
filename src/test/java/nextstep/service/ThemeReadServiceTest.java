package nextstep.service;

import nextstep.domain.theme.Theme;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.error.ApplicationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ThemeReadServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    @InjectMocks
    private ThemeReadService themeReadService;

    @Test
    void 존재하지_않는_이름으로_테마를_조회할_경우_예외가_발생한다() {
        // given
        String invalidName = "베니스 상인의 저택";

        given(themeRepository.findByName(eq(invalidName)))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> themeReadService.findThemeByName(invalidName))
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
        Theme findByName = themeReadService.findThemeByName(themeName);

        // then
        assertThat(findByName).usingRecursiveComparison()
                .isEqualTo(savedTheme);
    }

}
