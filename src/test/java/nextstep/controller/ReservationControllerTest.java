package nextstep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import nextstep.entity.ThemeConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@AutoConfigureMockMvc
@SpringBootTest
class ReservationControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    Long id = null;

    ReservationRequestDTO requestDTO = new ReservationRequestDTO(LocalDate.parse("2022-08-11"),
            LocalTime.parse("13:00:00"), "류성현", 1L);

    @PostConstruct
    void initTheme() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("desc", ThemeConstants.THEME_DESC);
        parameters.put("name", ThemeConstants.THEME_NAME);
        parameters.put("price", ThemeConstants.THEME_PRICE);
        new SimpleJdbcInsert(jdbcTemplate).withTableName("THEME").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(parameters).longValue();
    }

    @BeforeEach
    void init() throws Exception {
        jdbcTemplate.update("DELETE FROM RESERVATION");
        id = getId(objectMapper.writeValueAsString(requestDTO));
    }

    @Test
    @DisplayName("/reservations post 중복되는 요청시 에러가 발생한다.")
    void 중복된_POST_요청시_에러가_발생한다() throws Exception {
        //given
        String json = objectMapper.writeValueAsString(requestDTO);

        //expected
        mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error.messages").value("날짜와 시간이 중복되는 예약은 생성할 수 없습니다.")).andDo(print());

    }

    @Test
    @DisplayName("/reservations post 성공")
    void reservations_POST_성공_테스트() throws Exception {
        //given
        ReservationRequestDTO requestDTO2 = new ReservationRequestDTO(LocalDate.parse("2022-08-12"),
                LocalTime.parse("13:00:00"), "류성현", 1L);
        String json2 = objectMapper.writeValueAsString(requestDTO2);

        //expected
        mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON).content(json2))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/reservations/%d", id + 1L)))
                .andDo(print());

        List<Reservation> result = jdbcTemplate.query("SELECT * FROM RESERVATION WHERE id = ?",
                reservationRowMapper(), id + 1);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(id + 1);
        assertThat(result.get(0).getDate()).isEqualTo(requestDTO2.getDate());
        assertThat(result.get(0).getTime()).isEqualTo(requestDTO2.getTime());
        assertThat(result.get(0).getName()).isEqualTo(requestDTO2.getName());

    }

    @Test
    @DisplayName("/reservations/{id} get 성공시 해당하는 객체를 반환한다.")
    void reservations_get_성공_테스트() throws Exception {
        //expected
        mockMvc.perform(get("/reservations/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2022-08-11"))
                .andExpect(jsonPath("$.time").value("13:00:00"))
                .andExpect(jsonPath("$.name").value("류성현"))
                .andExpect(jsonPath("$.themeName").value(ThemeConstants.THEME_NAME))
                .andExpect(jsonPath("$.themeDesc").value(ThemeConstants.THEME_DESC))
                .andExpect(jsonPath("$.themePrice").value(ThemeConstants.THEME_PRICE)).andDo(print());
    }

    @Test
    @DisplayName("/reservations/{id} delete 요청시 해당하는 id의 객체가 사라진다")
    void reservations_delete는_id에_해당하는_객체를_제거한다() throws Exception {
        //when
        mockMvc.perform(delete("/reservations/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //expected
        assertThat(
                jdbcTemplate.query("SELECT * FROM RESERVATION WHERE id = ?", (rs, rowNum) -> rs.getLong(1), id)
                        .isEmpty()).isTrue();
    }

    private Long getId(String json) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        post("/reservations").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn();
        String[] locations = mvcResult.getResponse().getHeader("Location").split("/");
        return Long.parseLong(locations[locations.length - 1]);
    }

    private static RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) ->
                Reservation.creteReservation(new Reservation(rs.getDate("date").toLocalDate(),
                                rs.getTime("time").toLocalTime(), rs.getString("name"),
                                new Theme(ThemeConstants.THEME_NAME, ThemeConstants.THEME_DESC, ThemeConstants.THEME_PRICE)),
                        rs.getLong("id"));

    }


}