package nextstep.domain.repository;

import nextstep.domain.Reservation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class JdbcTemplateReservationRepositoryTest {

    @Autowired
    private JdbcTemplateReservationRepository jdbcTemplateReservationRepository;

    @BeforeAll
    static void createTable(@Autowired DataSource dataSource) {
        ClassPathResource resource = new ClassPathResource("reservation-schema.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }

    @Test
    void foo() {

    }

    @Test
    void 예약을_생성한다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", ThemeRepository.getDefaultTheme());

        // when
        Reservation savedReservation = jdbcTemplateReservationRepository.save(reservation);

        // then
        assertThat(savedReservation).isNotNull();
        assertThat(savedReservation.getId()).isOne();
    }

    @Test
    void 예약을_id로_조회한다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", ThemeRepository.getDefaultTheme());
        Reservation savedReservation = jdbcTemplateReservationRepository.save(reservation);
        Long id = savedReservation.getId();

        // when
        Reservation findById = jdbcTemplateReservationRepository.findById(id)
                .orElseThrow();

        // then
        assertThat(savedReservation).usingRecursiveComparison()
                .isEqualTo(findById);
    }

    @Test
    void id에_해당하는_예약이_없을_경우_아무것도_반환되지_않는다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", ThemeRepository.getDefaultTheme());
        Reservation savedReservation = jdbcTemplateReservationRepository.save(reservation);
        Long invalidId = savedReservation.getId() + 1000;

        // when
        Optional<Reservation> findById = jdbcTemplateReservationRepository.findById(invalidId);

        // then
        assertThat(findById).isEqualTo(Optional.empty());
    }

    @Test
    void 날짜와_시간이_같은_예약을_조회한다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", ThemeRepository.getDefaultTheme());
        jdbcTemplateReservationRepository.save(reservation);

        // when, then
        assertThat(jdbcTemplateReservationRepository.existsByDateAndTime(reservation.getDate(), reservation.getTime())).isTrue();
    }

    @Test
    void id에_해당하는_예약을_삭제한다() {
        // given
        Reservation reservation = new Reservation(LocalDate.parse("2023-01-09"), LocalTime.parse("13:00"), "eddie-davi", ThemeRepository.getDefaultTheme());
        Long reservationId = jdbcTemplateReservationRepository.save(reservation).getId();

        // when, then
        assertThat(jdbcTemplateReservationRepository.deleteById(reservationId)).isTrue();
    }
}
