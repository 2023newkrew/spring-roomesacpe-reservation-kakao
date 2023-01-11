package nextstep.main.java.nextstep.console;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.repository.ReservationRepository;
import nextstep.main.java.nextstep.repositoryUtil.ReservationPreparedStatementCreator;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ReservationDAO implements ReservationRepository {
    private static final int ONE = 1;
    Connection con = null;

    @Override
    public Reservation save(Reservation reservation) {
        connect();
        try {
            PreparedStatement preparedStatement = ReservationPreparedStatementCreator.insertReservationPreparedStatement(con, reservation);
            preparedStatement.executeUpdate();
            return new Reservation(getGeneratedKeys(preparedStatement), reservation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        connect();
        ResultSet rs;
        String sql = "SELECT * FROM reservation WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            rs.first();
            return Optional.of(Reservation.of(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public Boolean deleteOne(Long id) {
        connect();
        String sql = "DELETE FROM reservation WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            return ONE == ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        connect();
        String sql = "SELECT * FROM reservation WHERE date = ? AND time = ?";
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    private Long getGeneratedKeys(PreparedStatement ps) throws SQLException {
        ResultSet generatedKeys = ps.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getLong(1);
    }

    private void connect() {
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }
}
