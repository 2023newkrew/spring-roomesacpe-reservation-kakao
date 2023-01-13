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
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ThemeCreateRequest;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Reservation Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationTest {

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
                .content(this.objectMapper.writeValueAsString(new ThemeCreateRequest("워너고홈", "병맛 어드벤처 회사 코믹물", 29000)))
        );
        this.mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new ReservationCreateRequest("2022-08-11", "13:00", "kayla", 1L)))
        );
        this.mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new ReservationCreateRequest("2022-08-12", "13:00", "jerrie", 1L)))
        );
    }

    @DisplayName("예약 생성 테스트")
    @Test
    void createReservation() {
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest("2022-08-13", "13:00", "kayla", 1L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationCreateRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/3");
    }

    @DisplayName("예약 생성 거절 테스트")
    @Test
    void rejectCreateReservation() throws Exception {
        // Given
        ReservationCreateRequest overlapReservationCreateRequest = new ReservationCreateRequest("2022-08-11", "13:00", "jerrie", 1L);

        // When
        ResultActions overlapResultActions = mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(overlapReservationCreateRequest))
        );

        // Then
        overlapResultActions.andExpect(status().isUnprocessableEntity());
    }

    @DisplayName("예약 조회 테스트")
    @Test
    void showReservation() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("name", is("kayla"));
    }

    @DisplayName("예약 조회 거절 테스트")
    @Test
    void rejectShowReservation() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/10")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    void deleteReservation() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/2")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Disabled
    @DisplayName("예약 삭제 거절 테스트")
    @Test
    void rejectDeleteReservation() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/20")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}