package nextstep.dao;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationSaveForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ReservationJdbcTemplateDAOTest {
    private ReservationJdbcTemplateDAO reservationJdbcTemplateDAO;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationJdbcTemplateDAO = new ReservationJdbcTemplateDAO(jdbcTemplate);
    }

    @DisplayName("예약 생성")
    @Test
    void save() {
        ReservationSaveForm reservationSaveForm = new ReservationSaveForm(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0),
                "name3",
                1L
        );
        Long id = reservationJdbcTemplateDAO.save(reservationSaveForm);

        assertThat(id).isNotNull();
    }

    @DisplayName("id로 예약 조회")
    @Test
    void findById() {
        Reservation reservation = reservationJdbcTemplateDAO.findById(1L);

        assertThat(reservation).isNotNull();
        assertThat(reservation.getName()).isEqualTo("name");
    }

    @DisplayName("날짜 및 시간으로 예약 조회")
    @Test
    void findByDateAndTime() {
        List<Reservation> reservations = reservationJdbcTemplateDAO.findByDateAndTime(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0)
        );

        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getName()).isEqualTo("name");
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteById() {
        int rowCount = reservationJdbcTemplateDAO.deleteById(1L);

        assertThat(rowCount).isEqualTo(1);
    }
}
