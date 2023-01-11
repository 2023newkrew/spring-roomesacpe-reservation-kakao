package web.controller;

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
import web.dto.ReservationResponseDto;
import web.entity.Reservation;
import web.exception.ReservationDuplicateException;
import web.exception.ReservationNotFoundException;
import web.service.RoomEscapeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class RoomEscapeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoomEscapeService roomEscapeService;

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class Registration {

        private LocalDate today = LocalDate.now();

        @BeforeEach
        void setupMockingService() {
            when(roomEscapeService.reservation(any())).thenReturn(1L);
        }

        @Test
        void should_successfully_when_validRequest() throws Exception {
            String content = getValidContent();
            mockMvc.perform(post("/reservations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isCreated());
        }

        @Test
        void should_responseConflict_when_duplicateReservation() throws Exception {
            when(roomEscapeService.reservation(any())).thenThrow(ReservationDuplicateException.class);
            String content = getValidContent();
            mockMvc.perform(post("/reservations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isConflict());
        }

        @ParameterizedTest
        @MethodSource
        void should_responseBadRequest_when_invalidDateField(String content) throws Exception {
            mockMvc.perform(post("/reservations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest());
        }

        Stream<Arguments> should_responseBadRequest_when_invalidDateField() {
            return Stream.of(
                    Arguments.of(getContentWithDate("2022-111-12")),
                    Arguments.of(getContentWithDate("abc-111-12")),
                    Arguments.of(getContentWithDate("2022,11,12")),
                    Arguments.of(getContentWithDate("abcdef")),
                    Arguments.of(getContentWithDate("2023-01-08"))
            );
        }

        @ParameterizedTest
        @MethodSource
        void should_responseBadRequest_when_invalidTimeField(String content) throws Exception {
            mockMvc.perform(post("/reservations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest());
        }

        Stream<Arguments> should_responseBadRequest_when_invalidTimeField() {
            return Stream.of(
                    Arguments.of(getContentWithTime("13-00")),
                    Arguments.of(getContentWithTime("1f:30")),
                    Arguments.of(getContentWithTime("13:29")),
                    Arguments.of(getContentWithTime("10:30")),
                    Arguments.of(getContentWithTime("13:30:12")),
                    Arguments.of(getContentWithTime("21:00"))
            );
        }

        @ParameterizedTest
        @MethodSource
        void should_responseBadRequest_when_invalidNameField(String content) throws Exception {
            mockMvc.perform(post("/reservations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest());
        }

        Stream<Arguments> should_responseBadRequest_when_invalidNameField() {
            return Stream.of(
                    Arguments.of(getContentWithName("")),
                    Arguments.of(getContentWithName(" ")),
                    Arguments.of(getContentWithName("  ")),
                    Arguments.of(getContentWithName("   ")),
                    Arguments.of(getContentWithName("a".repeat(21)))
            );
        }

        private String getContentWithDate(String date) {
            return "{\n" +
                    "\t\"date\": \"" + date + "\",\n" +
                    "\t\"time\": \"13:00\",\n" +
                    "\t\"name\": \"name\"\n" +
                    "}";
        }

        private String getContentWithTime(String time) {
            today = today.plusDays(1);
            return "{\n" +
                    "\t\"date\": \"" + today + "\",\n" +
                    "\t\"time\": \"" + time + "\",\n" +
                    "\t\"name\": \"name\"\n" +
                    "}";
        }

        private String getContentWithName(String name) {
            today = today.plusDays(1);
            return "{\n" +
                    "\t\"date\": \"" + today + "\",\n" +
                    "\t\"time\": \"13:00\",\n" +
                    "\t\"name\": \"" + name + "\"\n" +
                    "}";
        }

        private String getValidContent() {
            today = today.plusDays(1);
            return "{\n" +
                    "\t\"date\": \"" + today + "\",\n" +
                    "\t\"time\": \"13:00\",\n" +
                    "\t\"name\": \"name\"\n" +
                    "}";
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class GetRegistration {

        @Test
        void should_successfully_when_validRequest() throws Exception {
            ReservationResponseDto responseDto = ReservationResponseDto.of(1,
                    Reservation.of(
                            LocalDate.of(2022, 8, 11),
                            LocalTime.of(13, 0),
                            "name"));
            when(roomEscapeService.findReservationById(anyLong())).thenReturn(responseDto);
            mockMvc.perform(get("/reservations/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.date").value("2022-08-11"))
                    .andExpect(jsonPath("$.time").value("13:00"))
                    .andExpect(jsonPath("$.name").value("name"))
                    .andExpect(jsonPath("$.themeName").value("워너고홈"))
                    .andExpect(jsonPath("$.themeDesc").value("병맛 어드벤처 회사 코믹물"))
                    .andExpect(jsonPath("$.themePrice").value(29000));
        }

        @Test
        void should_responseNotFound_when_notExistId() throws Exception {
            when(roomEscapeService.findReservationById(anyLong())).thenThrow(ReservationNotFoundException.class);
            mockMvc.perform(get("/reservations/-1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class CancelReservation {
        @Test
        void should_successfully_when_validRequest() throws Exception {
            doNothing().when(roomEscapeService).cancelReservation(anyLong());
            mockMvc.perform(delete("/reservations/1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        void shoud_responseNotFound_then_notExistId() throws Exception {
            doThrow(ReservationNotFoundException.class).when(roomEscapeService).cancelReservation(anyLong());
            mockMvc.perform(delete("/reservations/-1"))
                    .andExpect(status().isNotFound());
        }
    }

}
