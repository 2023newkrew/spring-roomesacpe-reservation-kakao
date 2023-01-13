package roomservice.controller;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMockMvc
@Transactional
public class ThemeControllerTest {

    MockMvc mock;
    @Autowired
    ThemeController themeController;
    @BeforeEach
    void setupMockMvc(){
        this.mock = MockMvcBuilders.standaloneSetup(themeController).build();
    }

    @Test
    void createTheme() throws Exception{
        mock.perform(post("/themes")
                .content("{\"name\" : \"이름\", \"desc\" : \"설명\", \"price\" : 20000}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", "/themes/2"))
//                Transactional로 인해서 roll-back되어도 auto_increment인 id가 2가 지워진 다음 3에 저장됨.
                .andDo(print());
        mock.perform(get("/themes")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    @Test
    void showTheme() throws Exception{
        mock.perform(post("/themes")
                .content("{\"name\" : \"이름\", \"desc\" : \"설명\", \"price\" : 20000}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
        mock.perform(get("/themes")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].name").value("워너고홈")) // data.sql에 미리 저장된 이름
                .andExpect(jsonPath("$[1]").exists())
                .andExpect(jsonPath("$[1].name").value("이름"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteTheme() throws Exception{
        mock.perform(delete("/themes/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        mock.perform(get("/themes")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1]").doesNotExist())
                .andDo(print())
                .andReturn();
    }
}
