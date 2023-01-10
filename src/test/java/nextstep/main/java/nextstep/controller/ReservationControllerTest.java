package nextstep.main.java.nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.service.ReservationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ReservationControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    private ReservationService reservationService;


    ReservationCreateRequestDto request = new ReservationCreateRequestDto(
            LocalDate.of(2023, 1, 9),
            LocalTime.of(1, 30),
            "name"
    );

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.registerParser("application/json", Parser.JSON);
    }

    @DisplayName("예약 생성 테스트")
    @Test
    void createReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("예약 조회 테스트")
    @Test
    void findOneReservationTest(){
        createReservation();

        RestAssured.given().log().all()
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id",Matchers.equalTo(1));
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    void deleteOneReservationTest() {
        createReservation();
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        assertThatThrownBy(() -> reservationService.findOneById(1L)).isInstanceOf(NoSuchReservationException.class);
    }

}
