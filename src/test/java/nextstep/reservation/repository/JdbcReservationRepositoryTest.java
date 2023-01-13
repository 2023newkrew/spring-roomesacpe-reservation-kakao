package nextstep.reservation.repository;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.dto.ReservationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@JdbcTest
class JdbcReservationRepositoryTest {
    final ReservationRepository repository;


    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class existsByDateAndTime {

        @DisplayName("생성 성공")
        @ParameterizedTest
        @MethodSource
        void should_idIs1_when_created(Reservation reservation) {
            boolean bool = repository.existsByDateAndTime(reservation);

        }

        List<Arguments> should_idIs1_when_created() {
            return List.of(
                    Arguments.of(new ReservationRequest("2022-08-12", "13:00", "류성현")),
                    Arguments.of(new ReservationRequest("2022-08-11", "14:00", "류성현")),
                    Arguments.of(new ReservationRequest("2022-08-11", "13:00", "pluto"))
            );
        }
    }
}