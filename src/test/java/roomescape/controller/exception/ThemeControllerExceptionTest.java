package roomescape.controller.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.Theme;

@DisplayName("예외 처리")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ThemeControllerExceptionTest {

    private static final String NAME_DATA1 = "워너고홈";
    private static final String NAME_DATA2 = "테스트 이름";
    private static final String DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int PRICE_DATA = 29000;

    private static final String THEME_PATH = "/theme";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("테마 생성) content-type이 application/json이 아닌 경우 값을 받지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.TEXT_HTML_VALUE,
            MediaType.TEXT_XML_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    void failToPostNotJson(String contentType) throws Exception {
        Theme theme = new Theme(NAME_DATA2, DESC_DATA, PRICE_DATA);

        mockMvc.perform(post(THEME_PATH)
                        .contentType(contentType)
                        .content(objectMapper.writeValueAsString(theme)))
                .andExpect(status().isUnsupportedMediaType())
                .andDo(print());
    }

    @DisplayName("테마 생성) 같은 이름의 예약은 생성 불가")
    @Test
    void failToPostAlreadyExist() throws Exception {
        Theme theme = new Theme(NAME_DATA1, DESC_DATA, PRICE_DATA);

        mockMvc.perform(post(THEME_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theme)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("테마 생성) 값의 포맷이 맞지 않을 경우 생성 불가")
    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\": \"TEST\",\"desc\": \"DESC\",\"price\": \"TEST\"}"})
    void failToPostInvalidFormat(String body) throws Exception {
        mockMvc.perform(post(THEME_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("테마 생성) 값이 포함되지 않았을 경우 생설 불가")
    @ParameterizedTest
    @ValueSource(strings = {
            "{\"desc\": \"DESC\",\"price\": 10000}",
            "{\"name\": \"TEST\"\"price\": 10000}",
            "{\"name\": \"TEST\",\"desc\": \"DESC\"}"})
    void failToPostNotExistingValue(String body) throws Exception {
        mockMvc.perform(post(THEME_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("테마 조회) ID가 없는 경우 조회 불가 및 ID가 잘못된 경우 (float, string)")
    @ParameterizedTest
    @ValueSource(strings = {
            THEME_PATH + "/10",
            THEME_PATH + "/1.1",
            THEME_PATH + "/test"})
    void failToGetNotExistingIdAndInvalidId(String path) throws Exception {
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("테마 삭제) ID가 없는 경우 조회 불가 및 ID가 잘못된 경우 또는 예약과 관계있는 테마 (float, string)")
    @ParameterizedTest
    @ValueSource(strings = {
            THEME_PATH + "/10",
            THEME_PATH + "/1.1",
            THEME_PATH + "/test",
            THEME_PATH + "/2"})
    void failToDeleteNotExistingIdAndInvalidIdAndReservationThemeId(String path) throws Exception {
        mockMvc.perform(delete(path)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
