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
public class ThemeWriteServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    @InjectMocks
    private ThemeWriteService themeWriteService;

    @Test
    void 테마_생성에_성공한다() {
        // given
        CreateThemeRequest createThemeRequest = new CreateThemeRequest("테마 이름", "테마 설명", 22_000);
        Theme theme = new Theme(5L, createThemeRequest.getName(), createThemeRequest.getDesc(), createThemeRequest.getPrice());

        given(themeRepository.save(any(Theme.class)))
                .willReturn(theme);

        // when
        Long themeId = themeWriteService.createTheme(createThemeRequest);

        // then
        assertThat(themeId).isEqualTo(theme.getId());
    }

    @Test
    void 동일한_이름을_가진_테마가_존재할_경우_테마_생성에_실패한다() {
        // given
        CreateThemeRequest createThemeRequest = new CreateThemeRequest("혜화 잡화점", "중복 테마 설명", 27_000);
        Theme savedTheme = new Theme(5L, "혜화 잡화점", "테마 설명", 22_000);

        given(themeRepository.findByName(eq(savedTheme.getName())))
                .willReturn(Optional.of(savedTheme));

        // when, then
        assertThatThrownBy(() -> themeWriteService.createTheme(createThemeRequest))
                .isInstanceOf(ApplicationException.class);
    }


}
