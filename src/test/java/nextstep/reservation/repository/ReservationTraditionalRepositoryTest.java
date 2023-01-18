package nextstep.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.repository.reservation.ReservationRepository;
import nextstep.reservation.repository.reservation.ReservationTraditionalRepository;
import nextstep.reservation.repository.theme.ThemeRepository;
import nextstep.reservation.repository.theme.ThemeTraditionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {"classpath:schema.sql"})
class ReservationTraditionalRepositoryTest {

    private final String URL = "jdbc:h2:~/tmp/test";
    private final String USER = "tester";
    private final String PASSWORD = "";
    private final ReservationRepository reservationRepository
            = new ReservationTraditionalRepository(URL, USER, PASSWORD);
    private final ThemeRepository themeRepository
            = new ThemeTraditionalRepository(URL, USER, PASSWORD);

    private final Theme testTheme = Theme.builder()
            .id(1L)
            .name("워너고홈")
            .desc("병맛 어드벤처 회사 코믹물")
            .price(29_000)
            .build();

    private final Reservation testReservation = Reservation.builder()
            .date(LocalDate.of(1982, 2, 19))
            .time(LocalTime.of(2, 2))
            .name("name")
            .theme(testTheme)
            .build();

    @BeforeEach
    void setUp() {
        initTable();
        themeRepository.add(testTheme);
    }

    private void initTable(){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "DROP TABLE IF EXISTS RESERVATION";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

            sql = "CREATE TABLE RESERVATION ( " +
                    "id bigint not null auto_increment, " +
                    "name     varchar(20)," +
                    "date date, " +
                    "time time, " +
                    "theme_id bigint not null, " +
                    "primary key (id))";

            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

            sql = "DROP TABLE IF EXISTS THEME";
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

            sql = "CREATE TABLE THEME (" +
                    "id      bigint not null auto_increment," +
                    "name    varchar(20)," +
                    "desc    varchar(255)," +
                    "price   int," +
                    "primary key (id))";

            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Test
    void 예약을_추가하면_예약_아이디가_반환됩니다() {
        assertThat(reservationRepository.add(testReservation)).isInstanceOf(Long.class);
    }

    @Test
    void 예약을_조회할_수_있습니다() {
        // when & given
        Long id = reservationRepository.add(testReservation);

        // then
        assertThat(reservationRepository.findById(id).get()).isInstanceOf(Reservation.class);
    }

    @Test
    void 전체_예약을_가져올_수_있습니다() {
        // when
        reservationRepository.add(Reservation.builder()
                .date(LocalDate.of(1982, 3, 19))
                .time(LocalTime.of(2, 2))
                .name("name")
                .theme(testTheme)
                .build());
        
        reservationRepository.add(Reservation.builder()
                .date(LocalDate.of(1982, 2, 19))
                .time(LocalTime.of(2, 3))
                .name("name")
                .theme(testTheme)
                .build());

        reservationRepository.add(Reservation.builder()
                .date(LocalDate.of(1982, 2, 19))
                .time(LocalTime.of(2, 4))
                .name("name")
                .theme(testTheme)
                .build());

        // given & then
        assertThat(reservationRepository.findAll().size()).isEqualTo(3);
    }


    @Test
    void 예약을_삭제할_수_있습니다() {
        // when
        Long id = reservationRepository.add(testReservation);

        // given
        assertThat(reservationRepository.delete(id)).isTrue();

        // then
        assertThat(reservationRepository.findById(id).isEmpty()).isTrue();
    }
}