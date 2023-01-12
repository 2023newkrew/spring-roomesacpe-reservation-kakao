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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationTraditionalRepositoryTest {

    private final String URL = "jdbc:h2:~/tmp/test";
    private final String USER = "tester";
    private final String PASSWORD = "";
    private final ReservationRepository repository = new ReservationTraditionalRepository(
            URL, USER, PASSWORD);

    private final Reservation testReservation = Reservation.builder()
            .date(LocalDate.of(1982, 2, 19))
            .time(LocalTime.of(2, 2))
            .name("name")
                .theme(new Theme("워너고홈 ", "병맛 어드벤처 회사 코믹물", 29000))
            .build();

    @BeforeEach
    void setUp() {
        initTable();
    }

    private void initTable(){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "DROP TABLE IF EXISTS RESERVATION";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

            sql = "CREATE TABLE IF NOT EXISTS RESERVATION ( " +
                    "id bigint not null auto_increment, " +
                    "date date, " +
                    "time time, " +
                    "name varchar(20), " +
                    "theme_name varchar(20), " +
                    "theme_desc varchar(255), " +
                    "theme_price int, " +
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
        assertThat(repository.add(testReservation)).isInstanceOf(Long.class);
    }

    @Test
    void 예약을_조회할_수_있습니다() {
        // when & given
        Long id = repository.add(testReservation);

        // then
        assertThat(repository.findById(id).get()).isInstanceOf(Reservation.class);
    }

    @Test
    void 전체_예약을_가져올_수_있습니다() {
        // when
        repository.add(Reservation.builder()
                .date(LocalDate.of(1982, 2, 19))
                .time(LocalTime.of(2, 2))
                .name("name")
                .theme(new Theme("워너고홈 ", "병맛 어드벤처 회사 코믹물", 29000))
                .build());
        repository.add(Reservation.builder()
                .date(LocalDate.of(1982, 2, 19))
                .time(LocalTime.of(2, 3))
                .name("name")
                .theme(new Theme("워너고홈 ", "병맛 어드벤처 회사 코믹물", 29000))
                .build());
        repository.add(Reservation.builder()
                .date(LocalDate.of(1982, 2, 19))
                .time(LocalTime.of(2, 4))
                .name("name")
                .theme(new Theme("워너고홈 ", "병맛 어드벤처 회사 코믹물", 29000))
                .build());

        // given & then
        assertThat(repository.findAll().size()).isEqualTo(3);
    }


    @Test
    void 예약을_삭제할_수_있습니다() {
        // when
        Long id = repository.add(testReservation);

        // given
        assertThat(repository.delete(id)).isTrue();

        // then
        assertThat(repository.findById(id).isEmpty()).isTrue();
    }
}