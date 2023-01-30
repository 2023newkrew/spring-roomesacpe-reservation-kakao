package roomescape.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.Reservation;


@DisplayName("예약 웹 요청 / 응답 처리")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReservationControllerTest {

    private static final LocalDate DATE_DATA1 = LocalDate.parse("2022-08-01");
    private static final LocalDate DATE_DATA2 = LocalDate.parse("2022-08-02");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final Long THEME_ID_DATA = 1L;

    private static final String SECOND_STRING = ":00";

    private static final String RESERVATIONS_PATH = "/reservations";
    private static final String FIRST_RESERVATION_PATH = RESERVATIONS_PATH + "/1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("예약 생성")
    @Test
    void postReservation() throws Exception {
        Reservation reservation = new Reservation(DATE_DATA2, TIME_DATA, NAME_DATA, THEME_ID_DATA);

        mockMvc.perform(post(RESERVATIONS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("예약 조회")
    @Test
    void getReservation() throws Exception {
        mockMvc.perform(get(FIRST_RESERVATION_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(DATE_DATA1.toString()))
                .andExpect(jsonPath("$.time").value(TIME_DATA + SECOND_STRING))
                .andExpect(jsonPath("$.name").value(NAME_DATA))
                .andDo(print());
    }

    @DisplayName("예약 취소")
    @Test
    void deleteReservation() throws Exception {
        mockMvc.perform(delete(FIRST_RESERVATION_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

    }
}
