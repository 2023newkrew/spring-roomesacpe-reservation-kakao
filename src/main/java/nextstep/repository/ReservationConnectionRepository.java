package nextstep.repository;

import nextstep.dto.ConnectionHandler;
import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.entity.ThemeConstants.*;

public class ReservationConnectionRepository implements ReservationRepository {

    private final ConnectionHandler connectionHandler;

    public ReservationConnectionRepository() throws SQLException {
        this.connectionHandler = new ConnectionHandler();
    }

    @Transactional
    @Override
    public Long save(ReservationRequestDTO reservationRequestDTO) throws SQLException {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
        ps.setDate(1, Date.valueOf(reservationRequestDTO.getDate()));
        ps.setTime(2, Time.valueOf(reservationRequestDTO.getTime()));
        ps.setString(3, reservationRequestDTO.getName());
        ps.setString(4, THEME_NAME);
        ps.setString(5, THEME_DESC);
        ps.setInt(6, THEME_PRICE);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (!rs.next()) {
            throw new SQLException();
        }

        return rs.getLong(1);
    }

    @Override
    public Reservation findById(Long id) throws SQLException {
        String sql = "SELECT * FROM RESERVATION WHERE ID = ?";

        PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
        ps.setLong(1, id);

        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            throw new SQLException();
        }

        return new Reservation(rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                new Theme(rs.getString("theme_name"),
                        rs.getString("theme_desc"),
                        rs.getInt("theme_price")));
    }

    @Override
    public boolean existByDateAndTime(LocalDate date, LocalTime time) throws SQLException {
        String sql = "SELECT * FROM RESERVATION WHERE DATE = ? AND TIME = ? LIMIT 1";

        PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    @Override
    public int deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM RESERVATION WHERE ID = ?";

        PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
        ps.setLong(1, id);

        return ps.executeUpdate();
    }

    public void releaseConnection() throws SQLException {
        connectionHandler.release();
    }
}
