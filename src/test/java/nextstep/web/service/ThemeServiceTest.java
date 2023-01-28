package nextstep.web.service;

import nextstep.domain.Theme;
import nextstep.web.dto.ThemeRequestDto;
import nextstep.web.repository.ReservationRepository;
import nextstep.web.repository.ThemeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ThemeServiceTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    ThemeRepository themeRepository;

    @InjectMocks
    ThemeService themeService;

    Theme theme;

    ThemeRequestDto requestDto;

    @BeforeEach
    void setUp() {
        theme = new Theme(1L, "테마이름", "테마설명", 1000);
        requestDto = new ThemeRequestDto(
                "테마이름", "테마설명", 1000
        );
    }

    @Test
    void 예약을_생성할_수_있다() {
        when(themeRepository.save(any()))
                .thenReturn(1L);

        Assertions.assertThat(themeService.create(requestDto))
                .isEqualTo(1L);
    }

    @Test
    void 예약을_조회할_수_있다() {
        when(themeRepository.findAll())
                .thenReturn(List.of(theme));

        Assertions.assertThat(themeService.readAll())
                .hasSize(1);
        verify(themeRepository, times(1)).findAll();
    }

    @Test
    void 예약을_취소할_수_있다() {
        Long id = 1L;
        doNothing().when(themeRepository)
                .deleteById(anyLong());
        when(reservationRepository.findByThemeId(1L)).thenReturn(Optional.empty());


        Assertions.assertThatNoException()
                .isThrownBy(() -> themeService.delete(id));
        verify(themeRepository, times(1)).deleteById(id);
    }
}
