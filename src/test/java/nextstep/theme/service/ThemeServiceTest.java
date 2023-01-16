package nextstep.theme.service;


import nextstep.RoomEscapeWebApplication;
import nextstep.exception.ConstraintViolationException;
import nextstep.exception.EntityNotFoundException;
import nextstep.reservation.ThemeReservationMock;
import nextstep.reservation.dao.ThemeReservationDao;
import nextstep.reservation.entity.Reservation;
import nextstep.theme.ThemeMock;
import nextstep.theme.dto.ThemeDetail;
import nextstep.theme.dto.ThemeDto;
import nextstep.theme.entity.Theme;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = RoomEscapeWebApplication.class)
@Transactional
class ThemeServiceTest {
    public static final Long NOT_EXIST_THEME_ID = Long.MAX_VALUE;
    public static final Long EXIST_THEME_ID = 1L;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeReservationDao themeReservationDao;

    @Test
    @DisplayName("존재하지 않는 테마는 조회할 수 없다.")
    void test1() {
        Assertions.assertThatThrownBy(() -> themeService.findById(NOT_EXIST_THEME_ID))
            .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("생성된 테마는 조회할 수 있다.")
    void test2() {
        ThemeDto theme = ThemeMock.makeRandomThemeDto();

        Long themeId = themeService.save(theme);
        assertThat(themeService.findById(themeId)).isNotNull();
    }

    @Test
    @DisplayName("모든 테마를 조회할 수 있다.")
    void test3() {
        int themeSize = 10;
        List<ThemeDto> themes = ThemeMock.makeRandomThemeDto(themeSize, ThemeMock.getRandomNames(themeSize));
        themes.forEach(themeService::save);
        assertThat(themeService.findAll()).hasSize(themeSize + 1);
    }

    @Test
    @DisplayName("동일한 이름의 테마를 생성할 수 없다.")
    void test4() {
        String themeName = "theme1";
        ThemeDto theme1 = ThemeMock.makeRandomThemeDto(themeName);
        ThemeDto theme2 = ThemeMock.makeRandomThemeDto(themeName);

        themeService.save(theme1);
        Assertions.assertThatThrownBy(() -> themeService.save(theme2))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("테마 생성 후, id를 반환해야한다.")
    void test5() {
        ThemeDto theme = ThemeMock.makeRandomThemeDto();
        Long themeId = themeService.save(theme);

        assertThat(themeService.findById(themeId)).isNotNull();
    }

    @Test
    @DisplayName("수정된 테마는 데이터베이스에 반영되어야 한다.")
    void test6() {
        Theme theme = ThemeMock.makeRandomTheme();
        Long themeId = themeService.save(theme.toDto());

        Theme theme2 = ThemeMock.makeRandomTheme();
        theme2.setId(themeId);

        themeService.update(theme2.toDto());

        ThemeDetail updatedTheme = themeService.findById(themeId);
        assertThat(updatedTheme).isNotNull();

        assertThat(new ThemeDetail(theme2)).isEqualTo(updatedTheme);
    }

    @Test
    @DisplayName("삭제된 테마는 조회할 수 없다.")
    void test7() {
        ThemeDto theme = ThemeMock.makeRandomThemeDto();
        Long themeId = themeService.save(theme);

        themeService.deleteById(themeId);
        Assertions.assertThatThrownBy(() -> themeService.findById(themeId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 테마는 수정할 수 없다.")
    void test8() {
        ThemeDto themeDto = ThemeMock.makeRandomThemeDto();
        themeDto.setId(NOT_EXIST_THEME_ID);

        int updateCount = themeService.update(themeDto);
        assertThat(updateCount).isZero();
    }

    @Test
    @DisplayName("존재하지 않는 테마는 삭제할 수 없다.")
    void test9() {
        Assertions.assertThatThrownBy(() -> themeService.deleteById(NOT_EXIST_THEME_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("예약이 이미 존재하는 테마는 수정할 수 없다.")
    void test10() {
        Reservation reservation = ThemeReservationMock.makeRandomReservationDto(EXIST_THEME_ID).toEntity();
        themeReservationDao.insert(reservation);

        Theme theme = ThemeMock.makeRandomTheme();
        theme.setId(EXIST_THEME_ID);
        Assertions.assertThatThrownBy(() -> themeService.update(theme.toDto()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("예약이 이미 존재하는 테마는 삭제할 수 없다.")
    void test11() {
        Reservation reservation = ThemeReservationMock.makeRandomReservationDto(EXIST_THEME_ID).toEntity();
        themeReservationDao.insert(reservation);
        Assertions.assertThatThrownBy(() -> themeService.deleteById(EXIST_THEME_ID))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
