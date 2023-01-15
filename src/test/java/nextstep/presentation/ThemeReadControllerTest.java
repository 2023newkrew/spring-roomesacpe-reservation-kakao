package nextstep.presentation;

import nextstep.domain.theme.Theme;
import nextstep.dto.request.Pageable;
import nextstep.dto.response.FindThemeResponse;
import nextstep.service.ThemeReadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ThemeReadController.class)
public class ThemeReadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThemeReadService themeReadService;

    @Test
    void 테마_목록을_페이지_단위로_조회한다() throws Exception {
        // given
        int page = 0, size = 5;
        List<FindThemeResponse> findThemeResponses = Stream.of("혜화 잡화점", "거울의 방", "베니스 상인의 저택", "워너고홈", "명당")
                .map((themeName) -> FindThemeResponse.from(new Theme(themeName, "테마 설명", 22_000)))
                .collect(Collectors.toList());

        given(themeReadService.findAllTheme(any(Pageable.class)))
                .willReturn(findThemeResponses);

        // when
        ResultActions perform = mockMvc.perform(get("/themes?page=" + page + "&size=" + size));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(size)));
    }

}
