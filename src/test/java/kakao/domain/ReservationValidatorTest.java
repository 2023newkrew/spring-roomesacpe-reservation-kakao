package kakao.domain;

import domain.ReservationValidator;
import kakao.dto.request.CreateReservationRequest;
import kakao.error.exception.IllegalCreateReservationRequestException;
import kakao.repository.ReservationJDBCRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
class ReservationValidatorTest {

    @Autowired
    ReservationJDBCRepository reservationJDBCRepository;

    private ReservationValidator reservationValidator;

    @BeforeEach
    void setUp() {
        this.reservationValidator = new ReservationValidator(reservationJDBCRepository);
    }

    @DisplayName("날짜보다 이전으로 request 요청을 보내면 InvalidRequest 예외를 보낸다")
    @Test
    void createInvalidDate() {
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDate.of(2022, 10, 23),
                LocalTime.of(13, 00),
                "baker",
                1L
        );

        Assertions.assertThatExceptionOfType(IllegalCreateReservationRequestException.class)
                .isThrownBy(() -> reservationValidator.validateForCreate(request));
    }

}
