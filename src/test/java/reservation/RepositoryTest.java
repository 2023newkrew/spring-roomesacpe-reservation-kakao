package reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import reservation.model.domain.Reservation;
import reservation.model.domain.Theme;
import reservation.model.dto.RequestReservation;
import reservation.respository.ReservationJdbcTemplateRepository;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
public class RepositoryTest {

    private ReservationJdbcTemplateRepository reservationJdbcTemplateRepository;
    private RequestReservation requestReservation;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RepositoryTest() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        LocalTime time = LocalTime.of(11, 0);

        requestReservation = new RequestReservation(date, time, "name", 1L);
    }

    @BeforeEach
    void setUp() {
        reservationJdbcTemplateRepository = new ReservationJdbcTemplateRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("예약 생성이 되어야 한다.")
    void save() {
         Long id = reservationJdbcTemplateRepository.save(makeReservationBeforeStore(1L, requestReservation));
         assertThat(id).isGreaterThan(0);
    }

    @Test
    @DisplayName("생성된 예약을 조회할 수 있어야 한다.")
    void find() {
        Long id = reservationJdbcTemplateRepository.save(makeReservationBeforeStore(1L, requestReservation));
        Reservation before = makeReservationBeforeStore(id, requestReservation);
        Reservation after = reservationJdbcTemplateRepository.findById(id);
        assertThat(before).isEqualTo(after);
    }

    @Test
    @DisplayName("생성된 예약을 취소할 수 있어야 한다.")
    void delete() {
        Long id = reservationJdbcTemplateRepository.save(makeReservationBeforeStore(1L, requestReservation));
        int rowCount = reservationJdbcTemplateRepository.deleteById(id);
        assertThat(rowCount).isEqualTo(1);
    }

    @Test
    @DisplayName("시간과 날짜가 중복되는 예약은 불가능하다.")
    void duplicate(){
        Long id = reservationJdbcTemplateRepository.save(makeReservationBeforeStore(1L, requestReservation));
        assertThat(reservationJdbcTemplateRepository.existByDateTimeTheme(requestReservation.getDate(), requestReservation.getTime(), 1L))
                .isTrue();
    }

    private Reservation makeReservationBeforeStore(Long id, RequestReservation req) {
        return new Reservation(id, req.getDate(), req.getTime(), req.getUsername(), 1L);
    }
}
