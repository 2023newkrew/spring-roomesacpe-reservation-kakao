package nextstep.web.repository.database;

import nextstep.domain.Reservation;
import nextstep.web.exception.BusinessException;
import nextstep.web.exception.CommonErrorCode;
import nextstep.web.repository.ReservationRepository;

import java.sql.*;
import java.util.NoSuchElementException;

public class ReservationDbRepository implements ReservationRepository {

    public static final String JDBC_URL = "jdbc:h2:~/test;AUTO_SERVER=true";
    public static final String JDBC_USER = "sa";
    public static final String JDBC_PASSWORD = "";

    public ReservationDbRepository() {
    }

    @Override
    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            System.out.println("정상적으로 연결되었습니다.");
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme()
                    .getName());
            ps.setString(5, reservation.getTheme()
                    .getDesc());
            ps.setInt(6, reservation.getTheme()
                    .getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new BusinessException(CommonErrorCode.SQL_CONNECTION_ERROR);
        }
    }

    @Override
    public Reservation findById(Long id) {
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM reservation WHERE ID = ?;";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            validateFindResult(resultSet);
            return Reservation.from(resultSet);
        } catch (SQLException e) {
            throw new BusinessException(CommonErrorCode.SQL_CONNECTION_ERROR);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "DELETE FROM reservation WHERE ID = ?;";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new BusinessException(CommonErrorCode.SQL_CONNECTION_ERROR);
        }
    }

    private void validateFindResult(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new NoSuchElementException();
        }
    }
}
