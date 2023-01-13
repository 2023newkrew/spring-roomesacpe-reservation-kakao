package nextstep.main.java.nextstep.mvc.controller.theme;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateOrUpdateRequest;
import nextstep.main.java.nextstep.mvc.service.reservation.ReservationService;
import nextstep.main.java.nextstep.mvc.service.theme.ThemeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ThemeController.class)
public class ThemeControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    ThemeService themeService;
    @MockBean
    ReservationService reservationService;

    @Test
    @DisplayName("성공적으로 테마가 생성된다.")
    void createSuccess() throws Exception {
        // given
        Long id = 1L;
        ThemeCreateOrUpdateRequest request = ThemeCreateOrUpdateRequest.of("theme", "description of new theme", 10000);
        String content = objectMapper.writeValueAsString(request);

        given(themeService.save(any(ThemeCreateOrUpdateRequest.class)))
                .willReturn(id);

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/themes")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("Location")).isEqualTo("/themes/" + id);
    }

    @ParameterizedTest
    @MethodSource({"provideRequest"})
    @DisplayName("올바른 값이 들어오지 않을 경우 테마 생성에 실패한다.")
    void create(ThemeCreateOrUpdateRequest request) throws Exception {
        // given
        Long id = 1L;
        String content = objectMapper.writeValueAsString(request);

        given(themeService.save(any(ThemeCreateOrUpdateRequest.class)))
                .willReturn(id);

        // when
        MvcResult result = mockMvc.perform(post("/themes")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // then
        assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    static Stream<Arguments> provideRequest() {
        String validName = "테마명";

        String validDesc = "테마의 설명은 10자 이상으로 정성적으로 작성해야 합니다.";
        String invalidDesc = "테마 설명입니다.";

        int validPrice = 5000;
        int invalidPrice = 999;

        return Stream.of(
                Arguments.arguments(ThemeCreateOrUpdateRequest.of(validName, validDesc, invalidPrice)),
                Arguments.arguments(ThemeCreateOrUpdateRequest.of(validName, invalidDesc, validPrice)),
                Arguments.arguments(ThemeCreateOrUpdateRequest.of("", validDesc, validPrice))
        );
    }
}
