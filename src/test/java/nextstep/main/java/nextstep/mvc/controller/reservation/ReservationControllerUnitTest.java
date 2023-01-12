package nextstep.main.java.nextstep.mvc.controller.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.util.logging.Slf4j;
import nextstep.main.java.nextstep.mvc.controller.reservation.ReservationController;
import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.reservation.response.ReservationFindResponse;
import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.service.reservation.ReservationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(ReservationController.class)
public class ReservationControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    ReservationService reservationService;
    @Test
    @DisplayName("성공적으로 예약이 생성된다.")
    void createSuccess() throws Exception {
        // given
        ReservationCreateRequest request = getCreateRequest();
        String content = objectMapper.writeValueAsString(request);

        given(reservationService.save(any(ReservationCreateRequest.class)))
                .willReturn(1L);

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/reservations")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("Location")).isEqualTo("/reservations/1");
    }

    private ReservationCreateRequest getCreateRequest() {
        return ReservationCreateRequest.of(
                LocalDate.of(2023, 1, 19),
                LocalTime.of(3, 30),
                "test",
                "test");
    }

    @ParameterizedTest
    @MethodSource({"getRequest"})
    @DisplayName("올바른 값이 들어오지 않을 경우 예약 생성에 실패한다.")
    void create(ReservationCreateRequest request) throws Exception {
        // given
        Long id = 1L;
        String content = objectMapper.writeValueAsString(request);

        given(reservationService.save(any(ReservationCreateRequest.class)))
                .willReturn(id);

        // when
        MvcResult result = mockMvc.perform(post("/reservations")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // then
        assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    static Stream<Arguments> getRequest() {
        LocalDate validDate = LocalDate.of(2024, 1, 1);
        LocalDate invalidDate = LocalDate.of(2022, 1, 1);

        LocalTime time = LocalTime.of(3, 30);

        String validName = "name";
        String invalidName = "n";

        String themeName = "theme";

        return Stream.of(
                Arguments.arguments(ReservationCreateRequest.of(validDate, time, validName, "")),
                Arguments.arguments(ReservationCreateRequest.of(validDate, time, invalidName, themeName)),
                Arguments.arguments(ReservationCreateRequest.of(invalidDate, time, validName, themeName))
        );
    }
}
