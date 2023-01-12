package nextstep.main.java.nextstep.controller.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import nextstep.main.java.nextstep.mvc.service.reservation.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ReservationControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Reservation reservation;

    @MockBean
    ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservation = new Reservation(1L, LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", null);
    }

    @Test
    @DisplayName("컨트롤러 create() 테스트")
    void create() throws Exception {
        ReservationCreateRequest request = new ReservationCreateRequest(
                LocalDate.of(2023, 1, 9),
                LocalTime.of(1, 30),
                "name"
        );
        String content = objectMapper.writeValueAsString(request);
        given(reservationService.save(any(ReservationCreateRequest.class))).willReturn(reservation);

        mockMvc.perform(post("/reservations")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/reservations/1"));
    }

    @Test
    @DisplayName("컨트롤러 findOne() 테스트")
    void findOne() throws Exception {
//        given(reservationService.findOneById(1L)).willReturn(reservation);

        mockMvc.perform(get("/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(reservation)));
    }

    @Test
    @DisplayName("컨트롤러 delete() 테스트")
    void delete() throws Exception {
        doNothing().when(reservationService).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservations/1"))
                .andExpect(status().isNoContent());
    }

}
