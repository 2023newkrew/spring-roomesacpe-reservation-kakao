package roomescape.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.DBTestHelper;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.dto.ReservationRequest;
import roomescape.exception.ErrorCode;
import roomescape.mapper.ReservationMapper;
import roomescape.repository.ReservationWebRepository;
import roomescape.repository.ThemeWebRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private DBTestHelper dbTestHelper;
    @Autowired private ReservationWebRepository reservationWebRepository;
    @Autowired private ThemeWebRepository themeWebRepository;

    @BeforeEach
    void setUp(){
        dbTestHelper.dbCleanUp("reservation");
        dbTestHelper.dbCleanUp("theme");
    }

    @DisplayName("[RESERVATION][POST] 예약 정상 생성")
    @Test
    void createReservationSuccess() throws Exception{
        //given
        themeWebRepository.save(new Theme(1L, "테마이름", "테마설명", 22000));

        ReservationRequest reservationRequest = new ReservationRequest(LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L);

        //when
        mvc.perform(
                post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequest))
        ).andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/reservations/1"));

        //then
        assertThat(reservationWebRepository.findOne(1L))
                .isPresent()
                .get()
                .isEqualTo(new Reservation(1L, LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L));
    }

    @DisplayName("[RESERVATION][POST] 존재하지 않는 테마에 예약 요청시 예약생성 실패")
    @Test
    void createReservationToInvalidTheme() throws Exception{
        //given
        ReservationRequest reservationRequest = new ReservationRequest(LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L);
        ErrorCode expected = ErrorCode.THEME_NOT_FOUND;

        //when & then
        mvc.perform(
                post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequest))
        ).andExpect(status().is(expected.getHttpStatus().value()))
                .andExpect(content().string(expected.getMessage()));
    }

    @DisplayName("[RESERVATION][POST] 타임테이블에 없는 시간대에 예약 요청시 예약생성 실패")
    @Test
    void createReservationToInvalidTimeTableFail() throws Exception{
        //given
        themeWebRepository.save(new Theme(1L, "테마이름", "테마설명", 22000));

        ReservationRequest reservationRequest = new ReservationRequest(LocalDate.of(2023, 1, 1), LocalTime.of(13, 01), "jacob", 1L);
        ErrorCode expected = ErrorCode.TIME_TABLE_NOT_AVAILABLE;

        //when & then
        mvc.perform(
                post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequest))
        ).andExpect(status().is(expected.getHttpStatus().value()))
                .andExpect(content().string(expected.getMessage()));
    }

    @DisplayName("[RESERVATION][POST] 다른 예약과 중복되는 시간대에 예약 요청시 예약생성 실패")
    @Test
    void creatReservationToDuplicatedTimeFail() throws Exception {
        //given
        themeWebRepository.save(new Theme(1L, "테마이름", "테마설명", 22000));
        reservationWebRepository.save(new Reservation(null, LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L));

        ReservationRequest reservationRequest = new ReservationRequest(LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L);
        ErrorCode expected = ErrorCode.DUPLICATED_RESERVATION;

        //when & then
        mvc.perform(
                post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequest))
        ).andExpect(status().is(expected.getHttpStatus().value()))
        .andExpect(content().string(expected.getMessage()));
    }

    @DisplayName("[RESERVATION][GET] 예약 정상 단건 조회")
    @Test
    void getReservationSuccess() throws Exception {
        //given
        Reservation reservation = new Reservation(1L, LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L);
        Theme theme = new Theme(1L, "themeName", "themeDesc", 22000);

        themeWebRepository.save(theme);
        reservationWebRepository.save(reservation);

        //when & then
        mvc.perform(
                get("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(ReservationMapper.INSTANCE.reservationToReservationResponse(reservation, theme))
                        )
                );
    }

    @DisplayName("[RESERVATION][GET] 존재하지 않는 예약 조회시 예외 반환")
    @Test
    void getNonExistsReservationFail() throws Exception {
        //given
        ErrorCode expected = ErrorCode.RESERVATION_NOT_FOUND;

        //when & then
        mvc.perform(
                get("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(expected.getHttpStatus().value()))
                .andExpect(content().string(expected.getMessage()));
    }

    @DisplayName("[RESERVATION][DELETE] 예약 정상 삭제")
    @Test
    void deleteReservationSuccess() throws Exception {
        //given
        reservationWebRepository.save(new Reservation(null, LocalDate.of(2023, 1, 1), LocalTime.of(13, 00), "jacob", 1L));

        //when
        mvc.perform(
                delete("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(HttpStatus.NO_CONTENT.value()));

        //then
        assertThat(reservationWebRepository.findOne(1L)).isEmpty();
    }
}
