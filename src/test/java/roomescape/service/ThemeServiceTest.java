package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ThemeRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class ThemeServiceTest {
    @Autowired
    private ThemeService themeService;
    @Autowired
    private ReservationService reservationService;

    @DisplayName("Theme 저장 후 조회 가능함을 확인")
    @Test
    @Transactional
    public void createAndFindThemeTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테스트테마", "Lorem Ipsum", 1000);
        //when
        Long themeId = themeService.createTheme(themeRequestDto);
        //then
        Assertions.assertThatNoException().isThrownBy(() -> themeService.findTheme(themeId));
    }

    @DisplayName("중복된 이름의 theme 저장 시 예외 발생")
    @Test
    @Transactional
    public void createDuplicateThemeTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테스트테마", "Lorem Ipsum", 1000);
        //when
        Long themeId = themeService.createTheme(themeRequestDto);
        //then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> themeService.createTheme(themeRequestDto));
    }

    @DisplayName("예약이 있는 theme 지우려하면 예외 발생")
    @Test
    @Transactional
    public void deleteThemeTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테스트테마", "Lorem Ipsum", 1000);
        Long themeId = themeService.createTheme(themeRequestDto);
        reservationService.createReservation(new ReservationRequestDto(LocalDate.now(), LocalTime.now(), "Tester", themeId));
        //when
        //then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> themeService.deleteTheme(themeId));
    }
}
