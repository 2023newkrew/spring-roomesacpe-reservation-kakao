package roomescape.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.Theme;

@DisplayName("테마 웹 요청 / 응답 처리")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ThemeControllerTest {

    private static final String NAME_DATA1 = "워너고홈";
    private static final String NAME_DATA2 = "테스트 이름";
    private static final String DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int PRICE_DATA = 29000;

    private static final String THEME_PATH = "/theme";
    private static final String FIRST_THEME_PATH = THEME_PATH + "/1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("테마 생성")
    @Test
    void postTheme() throws Exception {
        Theme theme = new Theme(NAME_DATA2, DESC_DATA, PRICE_DATA);

        mockMvc.perform(post(THEME_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theme)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("테마 목록 조회")
    @Test
    void getThemeList() throws Exception {
        mockMvc.perform(get(THEME_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value(NAME_DATA1))
                .andExpect(jsonPath("$[0].desc").value(DESC_DATA))
                .andExpect(jsonPath("$[0].price").value(PRICE_DATA))
                .andDo(print());
    }

    @DisplayName("테마 조회")
    @Test
    void getTheme() throws Exception {
        mockMvc.perform(get(FIRST_THEME_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NAME_DATA1))
                .andExpect(jsonPath("$.desc").value(DESC_DATA))
                .andExpect(jsonPath("$.price").value(PRICE_DATA))
                .andDo(print());
    }

    @DisplayName("테마 취소")
    @Test
    void deleteTheme() throws Exception {
        mockMvc.perform(delete(FIRST_THEME_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
