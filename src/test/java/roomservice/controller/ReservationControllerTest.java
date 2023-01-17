package roomservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import roomservice.exceptions.WholeProgramAdvice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Http Method")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class ReservationControllerTest {
    @Autowired
    ReservationController reservationController;
    @Autowired
    WholeProgramAdvice reservationAdvice;
    private MockMvc mock;
    private String createBody;

    @BeforeEach
    void setUp() {
        mock = MockMvcBuilders.standaloneSetup(reservationController)
                .setControllerAdvice(reservationAdvice)
                .alwaysDo(print())
                .build();
        createBody = "{\"name\" : \"testName\", \"date\" : \"2023-01-01\", \"time\" : \"11:30\", \"themeId\" : 1}";
    }

    @DisplayName("Http Method - POST")
    @Test
    void createReservation() throws Exception {
        MvcResult mvcResult = mock.perform(post("/reservations")
                .content(createBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        String header = mvcResult.getResponse().getHeader("Location");
        String[] splitHeader = header.split("/");
        Long id = Long.parseLong(splitHeader[splitHeader.length-1]);

        mock.perform(get("/reservations/"+id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.themeName").value("워너고홈"))
                .andDo(print());
    }

    @DisplayName("Http Method - POST Exception")
    @Test
    void createReservationDuplicateException() throws Exception{
        mock.perform(post("/reservations")
                        .content(createBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        mock.perform(post("/reservations")
                .content(createBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("Http Method - GET")
    @Test
    void showReservation() throws Exception{
        mock.perform(get("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("A"))
                .andExpect(jsonPath("$.themeName").value("워너고홈"))
                .andDo(print());
    }

    @DisplayName("Http Method - GET Exception")
    @Test
    void showReservationIfIdNotExist() throws Exception{
        mock.perform(get("/reservations/10000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print());
    }

    @DisplayName("Http Method - DELETE")
    @Test
    void deleteReservation() throws Exception{
        mock.perform(delete("/reservations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
        mock.perform(get("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print());
    }
}
