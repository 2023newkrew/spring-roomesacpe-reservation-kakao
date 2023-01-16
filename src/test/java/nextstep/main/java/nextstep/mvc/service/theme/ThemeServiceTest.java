package nextstep.main.java.nextstep.mvc.service.theme;

import nextstep.main.java.nextstep.global.exception.exception.AlreadyReservedThemeException;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchThemeException;
import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.domain.theme.ThemeMapper;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateOrUpdateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.response.ThemeFindResponse;
import nextstep.main.java.nextstep.mvc.repository.theme.ThemeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ThemeServiceTest {
    @InjectMocks
    private ThemeService themeService;
    @Spy
    private ThemeMapper themeMapper;
    @Mock
    private ThemeRepository themeRepository;

    @Test
    @DisplayName("[테마 생성] 테마 생성 성공")
    void saveSuccess() {
        Long id = 1L;
        ThemeCreateOrUpdateRequest request = ThemeCreateOrUpdateRequest.of("theme", "description of new theme", 10000);
        Theme theme = createThemeFromIdAndRequest(id, request);

        given(themeRepository.save(any()))
                .willReturn(id);
        given(themeRepository.findById(id))
                .willReturn(Optional.ofNullable(theme));

        Long newThemeId = themeService.save(request);
        ThemeFindResponse response = themeService.findById(newThemeId);

        assertThat(response.getId()).isEqualTo(theme.getId());
        assertThat(response.getName()).isEqualTo(theme.getName());
        assertThat(response.getDesc()).isEqualTo(theme.getDesc());
        assertThat(response.getPrice()).isEqualTo(theme.getPrice());
    }

    @Test
    @DisplayName("[테마 조회] 존재하지 않는 테마는 조회 불가")
    void findNotExists() {
        Long id = 1L;
        given(themeRepository.findById(id))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> themeService.findById(id))
                .isInstanceOf(NoSuchThemeException.class);
    }

    @Test
    @DisplayName("[테마 조회] 모든 테마 조회")
    void findAll() {
        given(themeRepository.findAll())
                .willReturn(new ArrayList<>());

        assertThat(themeService.findAll()).isEmpty();
    }

    @Test
    @DisplayName("[테마 삭제] 존재하지 않는 테마는 삭제 불가")
    void deleteNotExists() {
        Long id = 1L;
        given(themeRepository.existsById(id))
                .willReturn(false);

        assertThatThrownBy(() -> themeService.deleteById(id, false))
                .isInstanceOf(NoSuchThemeException.class);
    }

    @Test
    @DisplayName("[테마 삭제] 예약이 존재하는 테마는 삭제 불가")
    void deleteAlreadyReserved() {
        Long id = 1L;

        assertThatThrownBy(() -> themeService.deleteById(id, true))
                .isInstanceOf(AlreadyReservedThemeException.class);
    }

    private Theme createThemeFromIdAndRequest(Long id, ThemeCreateOrUpdateRequest request) {
        return Theme.builder()
                .id(id)
                .name(request.getName())
                .desc(request.getDesc())
                .price(request.getPrice())
                .build();
    }
}
