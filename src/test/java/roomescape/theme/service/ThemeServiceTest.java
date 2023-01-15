package roomescape.theme.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.theme.domain.Theme;
import roomescape.theme.dto.request.ThemeRequestDTO;
import roomescape.theme.exception.DuplicatedThemeException;
import roomescape.theme.repository.ThemeRepository;

@ExtendWith(MockitoExtension.class)
class ThemeServiceTest {

    @InjectMocks
    private ThemeService themeService;

    @Mock
    private ThemeRepository themeRepository;

    private final Theme mockedTheme = Theme.builder().build();
    private final ThemeRequestDTO themeRequestDTO = ThemeRequestDTO.builder()
            .name("name")
            .desc("desc")
            .price(1000)
            .build();

    @Test
    void saveWhenDuplicate() {
        when(themeRepository.findByName(any(String.class))).thenReturn(Optional.of(mockedTheme));

        assertThatThrownBy(() -> this.themeService.save(this.themeRequestDTO)).isInstanceOf(
                DuplicatedThemeException.class);
    }
}
