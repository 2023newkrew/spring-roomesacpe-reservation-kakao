package nextstep.service;

import nextstep.domain.theme.Theme;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.dto.request.CreateThemeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

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
}
