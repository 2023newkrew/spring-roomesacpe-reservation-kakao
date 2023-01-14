package roomescape.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.DBTestHelper;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.dto.ThemeRequest;
import roomescape.exception.ErrorCode;
import roomescape.mapper.ThemeMapper;
import roomescape.repository.ReservationWebRepository;
import roomescape.repository.ThemeWebRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ThemeControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private DBTestHelper dbTestHelper;
    @Autowired private ReservationWebRepository reservationWebRepository;
    @Autowired private ThemeWebRepository themeWebRepository;

    @BeforeEach
    void setUp(){
        dbTestHelper.dbCleanUp("reservation");
        dbTestHelper.dbCleanUp("theme");
    }

    @DisplayName("[THEME][POST] 테마 정상 생성")
    @Test
    void createThemeSuccess() throws Exception{
        //given
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);

        //when
        mvc.perform(
                post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(themeRequest))
        ).andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/themes/1"));

        //then

        assertThat(themeWebRepository.findOne(1L))
                .isPresent()
                .get()
                .isEqualTo(new Theme(1L, "테마이름", "테마설명", 22000));
    }

    @DisplayName("[THEME][GET] 테마 정상 단건 조회")
    @Test
    void getThemeSuccess() throws Exception {
        //given
        Theme theme = new Theme(1L, "themeName", "themeDesc", 22000);

        themeWebRepository.save(theme);

        //when & then
        mvc.perform(
                get("/themes/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ThemeMapper.INSTANCE.themeToThemeResponse(theme))));

    }

    @DisplayName("[THEME][GET] 존재하지 않는 테마 조회시 예외 반환")
    @Test
    void getNonExistsThemeFail() throws Exception{
        //given
        ErrorCode expected = ErrorCode.THEME_NOT_FOUND;

        //when & then
        mvc.perform(
                get("/themes/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(expected.getHttpStatus().value()))
                .andExpect(content().string(expected.getMessage()));

    }

    @DisplayName("[THEME][GET] 테마 목록 정상 조회")
    @Test
    void getThemesSuccess() throws Exception{
        //given
        Theme theme1 = new Theme(1L, "themeName1", "themeDesc2", 22000);
        Theme theme2 = new Theme(2L, "themeName1", "themeDesc2", 22000);

        themeWebRepository.save(theme1);
        themeWebRepository.save(theme2);

        //when & then
        mvc.perform(
                get("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(
                                List.of(
                                        ThemeMapper.INSTANCE.themeToThemeResponse(theme1),
                                        ThemeMapper.INSTANCE.themeToThemeResponse(theme2)
                                )
                        )
                ));
    }

    @DisplayName("[THEME][PUT] 테마 정상 수정")
    @Test
    void updateThemeSuccess() throws Exception{
        //given
        Theme original = new Theme(1L, "themeName", "themeDesc", 22000);
        Theme target = new Theme(1L, "themeNameNew", "themeDescNew", 22000);

        themeWebRepository.save(original);

        //when
        mvc.perform(
                put("/themes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new ThemeRequest(target.getName(), target.getDesc(), target.getPrice())
                                )
                        )
        ).andExpect(status().isOk());

        //then
        assertThat(themeWebRepository.findOne(1L))
                .isPresent()
                .get()
                .isEqualTo(target);

    }

    @DisplayName("[THEME][PUT] 존재하지 않는 테마 수정시 예외반환")
    @Test
    void updateNonExistsThemeFail() throws Exception{
        //given
        Theme theme = new Theme(1L, "themeName", "themeDesc", 22000);
        ErrorCode expected = ErrorCode.THEME_NOT_FOUND;

        //when & then
        mvc.perform(
                put("/themes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                        new ThemeRequest(theme.getName(), theme.getDesc(), theme.getPrice())
                                )
                        )
        ).andExpect(status().is(expected.getHttpStatus().value()))
                .andExpect(content().string(expected.getMessage()));
    }

    @DisplayName("[THEME][PUT] 예약이 있는 테마 수정 시 예외반환")
    @Test
    void updateThemeWithReservationFail() throws Exception{
        //given
        Theme theme = new Theme(1L, "themeName", "themeDesc", 22000);
        Theme target = new Theme(1L, "themeNameNew", "themeDescNew", 22000);
        Reservation reservation = new Reservation(1L, LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L);
        ErrorCode expected = ErrorCode.THEME_HAS_RESERVATION;

        themeWebRepository.save(theme);
        reservationWebRepository.save(reservation);

        //when & then
        mvc.perform(
                put("/themes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ThemeRequest(target.getName(), target.getDesc(), target.getPrice())))
        ).andExpect(status().is(expected.getHttpStatus().value()))
                .andExpect(content().string(expected.getMessage()));

    }

    @DisplayName("[THEME][DELETE] 테마 정상 삭제")
    @Test
    void deleteThemeSuccess() throws Exception{
        //given
        Theme theme = new Theme(1L, "themeName", "themeDesc", 22000);

        themeWebRepository.save(theme);

        //when
        mvc.perform(
                delete("/themes/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        //then
        assertThat(themeWebRepository.findOne(1L))
                .isNotPresent();
    }

    @DisplayName("[THEME][DELETE] 예약이 있는 테마 삭제 시 예외반환")
    @Test
    void deleteThemeWithReservationFail() throws Exception{
        //given
        Theme theme = new Theme(1L, "themeName", "themeDesc", 22000);
        Reservation reservation = new Reservation(1L, LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L);
        ErrorCode expected = ErrorCode.THEME_HAS_RESERVATION;

        themeWebRepository.save(theme);
        reservationWebRepository.save(reservation);

        //when & then
        mvc.perform(
                delete("/themes/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(expected.getHttpStatus().value()))
                .andExpect(content().string(expected.getMessage()));
    }

}
