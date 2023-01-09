package nextstep.main.java.nextstep;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.repository.MemoryReservationRepository;
import nextstep.main.java.nextstep.repository.ReservationRepository;
import nextstep.main.java.nextstep.service.ReservationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationTest {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.registerParser("application/json", Parser.JSON);
    }

    @DisplayName("예약 생성 테스트")
    @Test
    void createReservation() {
        ReservationCreateRequestDto request = new ReservationCreateRequestDto(
                LocalDate.of(2023, 1, 9),
                LocalTime.of(1, 30),
                "name"
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("예약 조회 테스트")
    @Test
    void findOneReservationTest(){
        ReservationRepository repository = new MemoryReservationRepository();
        ReservationService reservationService = new ReservationService(repository);
        ReservationCreateRequestDto request = new ReservationCreateRequestDto(
                LocalDate.of(2023, 1, 9),
                LocalTime.of(1, 30),
                "name"
        );

        reservationService.save(request);

        RestAssured.given().log().all()
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id",Matchers.equalTo(1));
    }

    
}
