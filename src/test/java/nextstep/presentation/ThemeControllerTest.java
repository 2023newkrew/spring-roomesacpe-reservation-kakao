package nextstep.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.domain.theme.Theme;
import nextstep.dto.request.CreateThemeRequest;
import nextstep.dto.request.Pageable;
import nextstep.dto.response.FindThemeResponse;
import nextstep.error.ApplicationException;
import nextstep.error.ErrorType;
import nextstep.service.ThemeService;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(ThemeController.class)
public class ThemeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ThemeService themeService;

    @MockBean
    private ThemeRequestValidator themeRequestValidator;

    @Test
    void 테마_생성에_성공한다() throws Exception  {
        // given
        Long themeId = 6L;
        CreateThemeRequest createThemeRequest = new CreateThemeRequest("테마 이름", "테마 설명", 22_000);

        doNothing().when(themeRequestValidator)
                        .validateCreateRequest(any(CreateThemeRequest.class));
        given(themeService.createTheme(any(CreateThemeRequest.class)))
                .willReturn(themeId);

        // when
        ResultActions perform = mockMvc.perform(post("/themes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createThemeRequest)));

        // then
        MockHttpServletResponse response = perform.andReturn().getResponse();

        perform.andExpect(status().isCreated());
        assertThat(response.getHeader(HttpHeaders.LOCATION)).isEqualTo("/themes" + themeId);
    }

    @Test
    void 테마_정보가_모두_기입되지_않으면_테마_생성에_실패한다() throws Exception {
        // given
        Long themeId = 6L;
        CreateThemeRequest createThemeRequest = new CreateThemeRequest("테마 이름", "테마 설명", null);

        doThrow(new ApplicationException(ErrorType.INVALID_REQUEST_PARAMETER))
                .when(themeRequestValidator)
                .validateCreateRequest(any(CreateThemeRequest.class));

        // when
        ResultActions perform = mockMvc.perform(post("/themes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createThemeRequest)));

        // then
        perform.andExpect(status().isBadRequest());
        verify(themeService, times(0)).createTheme(any(CreateThemeRequest.class));
    }

    @Test
    void 테마_목록을_페이지_단위로_조회한다() throws Exception {
        // given
        int page = 0, size = 5;
        List<FindThemeResponse> findThemeResponses = Stream.of("혜화 잡화점", "거울의 방", "베니스 상인의 저택", "워너고홈", "명당")
                .map((themeName) -> FindThemeResponse.from(new Theme(themeName, "테마 설명", 22_000)))
                .collect(Collectors.toList());

        given(themeService.findAllTheme(any(Pageable.class)))
                .willReturn(findThemeResponses);

        // when
        ResultActions perform = mockMvc.perform(get("/themes?page=" + page + "&size=" + size));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(size)));
    }

}
