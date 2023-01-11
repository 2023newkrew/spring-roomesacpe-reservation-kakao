package nextstep.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.dto.request.CreateReservationRequest;
import nextstep.error.ApplicationException;
import nextstep.error.ErrorType;
import nextstep.service.ReservationService;
import nextstep.utils.ReservationRequestValidator;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ReservationRequestValidator reservationRequestValidator;

    @Test
    void 예약_생성에_성공한다() throws Exception {
        // given
        Long reservationId = 20L;
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-10", "13:00", "eddie", "테마 이름");

        doNothing().when(reservationRequestValidator)
                        .validateCreateRequest(any(CreateReservationRequest.class));
        given(reservationService.createReservation(any(CreateReservationRequest.class)))
                .willReturn(reservationId);

        // when
        ResultActions perform = mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createReservationRequest)));

        // then
        MockHttpServletResponse response = perform.andReturn().getResponse();

        perform.andExpect(status().isCreated());
        assertThat(response.getHeader(HttpHeaders.LOCATION)).isEqualTo("/reservations/" + reservationId);
    }

    @Test
    void 예약_정보_부족으로_예약에_실패한다() throws Exception {
        // given
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-10", null, "eddie", "테마 이름");

        doThrow(new ApplicationException(ErrorType.INVALID_RESERVATION_REQUEST_DATA))
                .when(reservationRequestValidator)
                .validateCreateRequest(any(CreateReservationRequest.class));

        // when
        ResultActions perform = mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createReservationRequest)));

        // then
        perform.andExpect(status().isBadRequest());
        verify(reservationService, times(0)).createReservation(any(CreateReservationRequest.class));
    }

}
