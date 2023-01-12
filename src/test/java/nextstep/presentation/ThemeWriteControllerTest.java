package nextstep.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.dto.request.CreateOrUpdateThemeRequest;
import nextstep.error.ApplicationException;
import nextstep.error.ErrorType;
import nextstep.service.ThemeWriteService;
import nextstep.utils.ThemeRequestValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(ThemeWriteController.class)
public class ThemeWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ThemeWriteService themeWriteService;

    @MockBean
    private ThemeRequestValidator themeRequestValidator;

    @Test
    void 테마_생성에_성공한다() throws Exception  {
        // given
        Long themeId = 6L;
        CreateOrUpdateThemeRequest createThemeRequest = new CreateOrUpdateThemeRequest("테마 이름", "테마 설명", 22_000);

        doNothing().when(themeRequestValidator)
                        .validateCreateOrUpdateRequest(any(CreateOrUpdateThemeRequest.class));
        given(themeWriteService.createTheme(any(CreateOrUpdateThemeRequest.class)))
                .willReturn(themeId);

        // when
        ResultActions perform = mockMvc.perform(post("/themes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createThemeRequest)));

        // then
        MockHttpServletResponse response = perform.andReturn().getResponse();

        perform.andExpect(status().isCreated());
        assertThat(response.getHeader(HttpHeaders.LOCATION)).isEqualTo("/themes/" + themeId);
    }

    @Test
    void 테마_정보가_모두_기입되지_않으면_테마_생성에_실패한다() throws Exception {
        // given
        Long themeId = 6L;
        CreateOrUpdateThemeRequest createThemeRequest = new CreateOrUpdateThemeRequest("테마 이름", "테마 설명", null);

        doThrow(new ApplicationException(ErrorType.INVALID_REQUEST_PARAMETER))
                .when(themeRequestValidator)
                .validateCreateOrUpdateRequest(any(CreateOrUpdateThemeRequest.class));

        // when
        ResultActions perform = mockMvc.perform(post("/themes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createThemeRequest)));

        // then
        perform.andExpect(status().isBadRequest());
        verify(themeWriteService, times(0)).createTheme(any(CreateOrUpdateThemeRequest.class));
    }

}
