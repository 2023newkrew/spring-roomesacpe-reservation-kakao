package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.domain.ThemeCreateRequestDto;
import nextstep.main.java.nextstep.exception.exception.DuplicateThemeException;
import nextstep.main.java.nextstep.repository.ThemeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepository;
    @InjectMocks
    private ThemeService themeService;

    @Test
    @DisplayName("테마 생성 기능 테스트")
    void createThemeTest() {
        ThemeCreateRequestDto themeCreateRequestDto = new ThemeCreateRequestDto("name", "desc", 0);
        Theme theme = new Theme(1L, themeCreateRequestDto.getName(), themeCreateRequestDto.getDesc(), themeCreateRequestDto.getPrice());

        when(themeRepository.save(any(Theme.class)))
                .thenReturn(theme);
        assertThat(themeService.createTheme(themeCreateRequestDto))
                .isEqualTo(theme);
    }

    @Test
    @DisplayName("중복 이름을 가진 테마 생성 시 예외 발생 테스트")
    void createDuplicateThemeTest(){
        ThemeCreateRequestDto themeCreateRequestDto = new ThemeCreateRequestDto("name", "desc", 0);
        Theme savedTheme = new Theme(1L, themeCreateRequestDto.getName(), "otherDesc", 1000);

        when(themeRepository.findByName(themeCreateRequestDto.getName())).thenReturn(Optional.of(savedTheme));

        assertThatCode(() -> themeService.createTheme(themeCreateRequestDto)).isInstanceOf(DuplicateThemeException.class);
    }

}
