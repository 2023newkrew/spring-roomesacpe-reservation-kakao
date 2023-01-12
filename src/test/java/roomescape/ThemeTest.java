package roomescape;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import roomescape.domain.Theme;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ThemeRequest;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Theme Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ThemeTest {

    @LocalServerPort
    int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    void setUp() throws Exception {
        RestAssured.port = port;
        this.mockMvc.perform(post("/theme")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new ThemeRequest("워너고홈", "병맛 어드벤처 회사 코믹물", 29000)))
        );
    }

    @DisplayName("테마 생성 테스트")
    @Test
    void createTheme() {
        ThemeRequest themeRequest = new ThemeRequest("해리포터", "코 없는 남성이 어린 소년에게 집착하는 호러물", 49000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/theme")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/theme/2");
    }

    @DisplayName("테마 조회 테스트")
    @Test
    void showTheme() throws Exception {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/theme/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("name", is("워너고홈"));
    }

    @DisplayName("테마 조회 거절 테스트")
    @Test
    void rejectShowReservation() throws Exception {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/theme/10")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("테마 수정 테스트")
    @Test
    void updateTheme() throws Exception {
        Theme modifiedTheme = new Theme(1L, "워너고홈", "병맛 어드벤처 회사 코믹물", 39000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(modifiedTheme)
                .when().put("/theme")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("price", is(39000));
    }

    @DisplayName("테마 수정 거절 테스트")
    @Test
    void rejectUpdateTheme() throws Exception {
        Theme modifiedTheme = new Theme(10L, "워너고홈", "병맛 어드벤처 회사 코믹물", 39000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(modifiedTheme)
                .when().put("/theme")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("테마 삭제 테스트")
    @Test
    void deleteReservation() throws Exception {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/theme/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
