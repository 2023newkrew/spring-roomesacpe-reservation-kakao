package web.theme.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import web.theme.exception.ThemeException;
import web.theme.service.ThemeService;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static web.theme.exception.ErrorCode.THEME_DUPLICATE;

@WebMvcTest(ThemeController.class)
@ExtendWith(MockitoExtension.class)
class ThemeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ThemeService themeService;

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class Save {

        @BeforeEach
        void setupMockingService() {
            when(themeService.save(any())).thenReturn(1L);
        }

        @Test
        void should_successfully_when_validRequest() throws Exception {
            String content = getValidContent();
            mockMvc.perform(post("/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location", "/themes/1"));
        }

        @Test
        void should_responseConflict_when_duplicateTheme() throws Exception {
            when(themeService.save(any())).thenThrow(new ThemeException(THEME_DUPLICATE));
            String content = getValidContent();
            mockMvc.perform(post("/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").value(THEME_DUPLICATE.getMessage()));
        }

        @ParameterizedTest
        @MethodSource
        void should_responseBadRequest_when_invalidNameField(String content) throws Exception {
            mockMvc.perform(post("/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest());
        }

        Stream<Arguments> should_responseBadRequest_when_invalidNameField() {
            return Stream.of(
                    Arguments.of(getContentWithName("")),
                    Arguments.of(getContentWithName(" ")),
                    Arguments.of(getContentWithName("  ")),
                    Arguments.of(getContentWithName("        ")),
                    Arguments.of(getContentWithName("a".repeat(21)))
            );
        }

        @ParameterizedTest
        @MethodSource
        void should_responseBadRequest_when_invalidDescField(String content) throws Exception {
            mockMvc.perform(post("/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest());
        }

        Stream<Arguments> should_responseBadRequest_when_invalidDescField() {
            return Stream.of(
                    Arguments.of(getContentWithDesc("")),
                    Arguments.of(getContentWithDesc("a".repeat(256)))
            );
        }

        @ParameterizedTest
        @MethodSource
        void should_responseBadRequest_when_invalidPriceField(String content) throws Exception {
            mockMvc.perform(post("/themes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest());
        }

        Stream<Arguments> should_responseBadRequest_when_invalidPriceField() {
            return Stream.of(
                    Arguments.of(getContentWithPrice(-1000)),
                    Arguments.of(getContentWithPrice(-1)),
                    Arguments.of(getContentWithPrice(1_000_001)),
                    Arguments.of(getContentWithPrice((int) Long.MAX_VALUE))
            );
        }

        private String getContentWithName(String name) {
            return "{\n" +
                    "\t\"name\": \"" + name + "\",\n" +
                    "\t\"desc\": \"테마설명\",\n" +
                    "\t\"price\": 22000\n" +
                    "}";
        }

        private String getContentWithDesc(String desc) {
            return "{\n" +
                    "\t\"name\": \"테마이름\",\n" +
                    "\t\"desc\": \"" + desc + "\",\n" +
                    "\t\"price\": 22000\n" +
                    "}";
        }

        private String getContentWithPrice(int price) {
            return "{\n" +
                    "\t\"name\": \"테마이름\",\n" +
                    "\t\"desc\": \"테마설명\",\n" +
                    "\t\"price\": " + price + "\n" +
                    "}";
        }

        private String getValidContent() {
            return "{\n" +
                    "\t\"name\": \"테마이름\",\n" +
                    "\t\"desc\": \"테마설명\",\n" +
                    "\t\"price\": 22000\n" +
                    "}";
        }
    }
}