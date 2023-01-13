package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationJdbcRepositoryTest {

    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/Projects/h2/db/roomescape;AUTO_SERVER=true";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    ReservationRepository repository;
    Theme testTheme;

    Reservation inputReservation1;
    Reservation inputReservation2;
    Reservation inputReservation3;

    Reservation expectedReservation1;
    Reservation expectedReservation2;
    Reservation expectedReservation3;

    @BeforeEach
    void setUp() throws SQLException {
        truncateAllTables();

        repository = new ReservationJdbcRepository();

        testTheme = new Theme("Theme", "Theme desc", 10_000);

        inputReservation1 = generateReservation(
                null, "2023-01-01", "13:00", "kim", testTheme);
        inputReservation2 = generateReservation(
                null, "2023-01-02", "14:00", "lee", testTheme);
        inputReservation3 = generateReservation(
                null, "2023-01-03", "15:00", "park", testTheme);

        expectedReservation1 = generateReservation(
                null, "2023-01-01", "13:00", "kim", testTheme);
        expectedReservation2 = generateReservation(
                null, "2023-01-02", "14:00", "lee", testTheme);
        expectedReservation3 = generateReservation(
                null, "2023-01-03", "15:00", "park", testTheme);

    }

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        Reservation savedReservation = repository.save(inputReservation1);

        expectedReservation1.setId(savedReservation.getId());

        assertThat(savedReservation).isEqualTo(expectedReservation1);
    }

    @DisplayName("여러개의 예약을 연속적으로 저장한다.")
    @Test
    void save_multi() {
        Reservation savedReservation1 = repository.save(inputReservation1);
        Reservation savedReservation2 = repository.save(inputReservation2);
        Reservation savedReservation3 = repository.save(inputReservation3);

        expectedReservation1.setId(savedReservation1.getId());
        expectedReservation2.setId(savedReservation2.getId());
        expectedReservation3.setId(savedReservation3.getId());

        assertThat(savedReservation1).isEqualTo(expectedReservation1);
        assertThat(savedReservation2).isEqualTo(expectedReservation2);
        assertThat(savedReservation3).isEqualTo(expectedReservation3);
    }

    @DisplayName("id로 예약을 조회한다 - 조회 성공")
    @Test
    void find_success() {
        Reservation savedReservation1 = repository.save(inputReservation1);
        Reservation savedReservation2 = repository.save(inputReservation2);
        Reservation savedReservation3 = repository.save(inputReservation3);

        expectedReservation1.setId(savedReservation1.getId());
        expectedReservation2.setId(savedReservation2.getId());
        expectedReservation3.setId(savedReservation3.getId());

        Optional<Reservation> result1 = repository.findById(savedReservation1.getId());
        Optional<Reservation> result2 = repository.findById(savedReservation2.getId());
        Optional<Reservation> result3 = repository.findById(savedReservation3.getId());

        assertThat(result1).isNotEmpty()
                .get().isEqualTo(expectedReservation1);
        assertThat(result2).isNotEmpty()
                .get().isEqualTo(expectedReservation2);
        assertThat(result3).isNotEmpty()
                .get().isEqualTo(expectedReservation3);
    }

    @DisplayName("id로 예약을 조회한다 - 조회 실패")
    @Test
    void find_fail() {
        Optional<Reservation> result = repository.findById(1L);
        assertThat(result).isEmpty();
    }

    @DisplayName("모든 예약을 조회한다")
    @Test
    void findAll() {
        Reservation savedReservation1 = repository.save(inputReservation1);
        Reservation savedReservation2 = repository.save(inputReservation2);
        Reservation savedReservation3 = repository.save(inputReservation3);

        expectedReservation1.setId(savedReservation1.getId());
        expectedReservation2.setId(savedReservation2.getId());
        expectedReservation3.setId(savedReservation3.getId());

        List<Reservation> result = repository.findAll();

        assertThat(result).contains(expectedReservation1, expectedReservation2, expectedReservation3);
    }

    @DisplayName("모든 예약을 조회한다 - 예약이 존재하지 않을 경우")
    @Test
    void findAll_empty() {
        List<Reservation> result = repository.findAll();
        assertThat(result).isEmpty();
    }

    @DisplayName("예약을 삭제한다 - 삭제 성공")
    @Test
    void delete_success() {
        Reservation savedReservation1 = repository.save(inputReservation1);

        boolean result = repository.delete(savedReservation1.getId());

        assertThat(result).isTrue();
        assertThat(repository.findById(savedReservation1.getId())).isEmpty();
    }

    @DisplayName("예약을 삭제한다 - 삭제할 대상 없음")
    @Test
    void delete_fail() {
        boolean result = repository.delete(1L);
        assertThat(result).isFalse();

    }

    private void truncateAllTables() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        List<String> truncateTableQueries = generateTruncateTableQueries(conn);

        executeTruncateTableQueries(conn, truncateTableQueries);

        conn.close();
    }

    private static void executeTruncateTableQueries(Connection conn, List<String> truncateQueries) throws SQLException {
        PreparedStatement setReferentialIntegrityFalse = conn.prepareStatement("SET REFERENTIAL_INTEGRITY FALSE");
        setReferentialIntegrityFalse.executeUpdate();
        setReferentialIntegrityFalse.close();

        for (String truncateQuery : truncateQueries) {
            PreparedStatement ps = conn.prepareStatement(truncateQuery);
            ps.executeUpdate();
            ps.close();
        }

        PreparedStatement setReferentialIntegrityTrue = conn.prepareStatement("SET REFERENTIAL_INTEGRITY TRUE");
        setReferentialIntegrityTrue.executeUpdate();
        setReferentialIntegrityTrue.close();
    }

    private static List<String> generateTruncateTableQueries(Connection conn) throws SQLException {
        String sql = "SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') AS query FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet truncateQueryResultSet = ps.executeQuery();

        List<String> truncateQueries = new ArrayList<>();
        while (truncateQueryResultSet.next()) {
            truncateQueries.add(truncateQueryResultSet.getString("query"));
        }

        truncateQueryResultSet.close();
        ps.close();

        return truncateQueries;
    }

    private Reservation generateReservation(Long id, String date, String time, String name, Theme theme) {
        return new Reservation(
                id,
                LocalDate.parse(date),
                LocalTime.parse(time),
                name,
                theme
        );
    }
}