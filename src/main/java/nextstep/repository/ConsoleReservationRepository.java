package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsoleReservationRepository implements ReservationRepository {

    private Connection con = null;

    public ConsoleReservationRepository() {
        connect();
    }

    private void connect() {
        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Reservation findById(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(findByIdSql, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return Reservation.from(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        PreparedStatement ps = con.prepareStatement(deleteByIdSql, new String[]{"id"});
        ps.setLong(1, id);
        int cnt = ps.executeUpdate();
        if (cnt == 0) throw new RuntimeException("해당 id의 예약은 존재하지 않습니다.");
    }

    @Override
    public Long save(LocalDate date, LocalTime time, String name, Theme theme) {
        try {
            validateReservation(date, time);

            PreparedStatement ps = getReservationPreparedStatement(con, date, time, name, theme);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
