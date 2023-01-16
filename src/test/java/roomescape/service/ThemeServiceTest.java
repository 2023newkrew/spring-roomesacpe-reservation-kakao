package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.*;

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

    @DisplayName("Theme의 이름 업데이트")
    @Test
    @Transactional
    public void updateNameTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테스트테마", "Lorem Ipsum", 1000);
        //when
        Long themeId = themeService.createTheme(themeRequestDto);
        themeService.updateTheme(themeId, new ThemeUpdateRequestDto("테스트테마2", null, null));
        ThemeResponseDto theme = themeService.findTheme(themeId);
        //then
        Assertions.assertThat(theme.getName()).isEqualTo("테스트테마2");
        Assertions.assertThat(theme.getDesc()).isNotNull();
        Assertions.assertThat(theme.getPrice()).isNotNull();
    }

    @DisplayName("Theme의 설명 업데이트")
    @Test
    @Transactional
    public void updateDescTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테스트테마", "Lorem Ipsum", 1000);
        //when
        Long themeId = themeService.createTheme(themeRequestDto);
        themeService.updateTheme(themeId, new ThemeUpdateRequestDto(null, "Lorem Ipsum2", null));
        ThemeResponseDto theme = themeService.findTheme(themeId);
        //then
        Assertions.assertThat(theme.getName()).isNotNull();
        Assertions.assertThat(theme.getDesc()).isEqualTo("Lorem Ipsum2");
        Assertions.assertThat(theme.getPrice()).isNotNull();
    }

    @DisplayName("Theme의 가격 업데이트")
    @Test
    @Transactional
    public void updatePriceTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테스트테마", "Lorem Ipsum", 1000);
        //when
        Long themeId = themeService.createTheme(themeRequestDto);
        themeService.updateTheme(themeId, new ThemeUpdateRequestDto(null, null, 2000));
        ThemeResponseDto theme = themeService.findTheme(themeId);
        //then
        Assertions.assertThat(theme.getName()).isNotNull();
        Assertions.assertThat(theme.getDesc()).isNotNull();
        Assertions.assertThat(theme.getPrice()).isEqualTo(2000);
    }

    @DisplayName("Theme의 이름과 가격 업데이트")
    @Test
    @Transactional
    public void updateNameAndPriceTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테스트테마", "Lorem Ipsum", 1000);
        //when
        Long themeId = themeService.createTheme(themeRequestDto);
        themeService.updateTheme(themeId, new ThemeUpdateRequestDto("테스트테마2", null, 2000));
        ThemeResponseDto theme = themeService.findTheme(themeId);
        //then
        Assertions.assertThat(theme.getName()).isEqualTo("테스트테마2");
        Assertions.assertThat(theme.getDesc()).isNotNull();
        Assertions.assertThat(theme.getPrice()).isEqualTo(2000);
    }

    @DisplayName("Theme 전부 업데이트")
    @Test
    @Transactional
    public void updateAllTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테스트테마", "Lorem Ipsum", 1000);
        //when
        Long themeId = themeService.createTheme(themeRequestDto);
        themeService.updateTheme(themeId, new ThemeUpdateRequestDto("테스트테마2", "Lorem Ipsum2", 2000));
        ThemeResponseDto theme = themeService.findTheme(themeId);
        //then
        Assertions.assertThat(theme.getName()).isEqualTo("테스트테마2");
        Assertions.assertThat(theme.getDesc()).isEqualTo("Lorem Ipsum2");
        Assertions.assertThat(theme.getPrice()).isEqualTo(2000);
    }

    @DisplayName("모든 Theme 조회 후 결과에 미리 추가한 항목 있는지 검사")
    @Test
    @Transactional
    public void findAllTest() {
        //given
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테스트테마", "Lorem Ipsum", 1000);
        //when
        Long themeId = themeService.createTheme(themeRequestDto);
        ThemeResponseDto theme = themeService.findTheme(themeId);
        ThemesResponseDto allTheme = themeService.findAllTheme();
        //then
        Assertions.assertThat(allTheme.getThemes()).contains(theme);
    }
}
