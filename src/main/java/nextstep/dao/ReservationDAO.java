package nextstep.dao;

import static nextstep.entity.ThemeConstants.THEME_DESC;
import static nextstep.entity.ThemeConstants.THEME_NAME;
import static nextstep.entity.ThemeConstants.THEME_PRICE;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import nextstep.dto.ConnectionHandler;
import nextstep.dto.ReservationRequestDTO;
import nextstep.exception.ConflictException;

public class ReservationDAO {

    private final ConnectionHandler connectionHandler;

    public ReservationDAO() {
        this.connectionHandler = new ConnectionHandler();
    }


    public Reservation addReservation(ReservationRequestDTO requestDTO) {
        validate(requestDTO);
        Reservation reservation = insertReservation(requestDTO);
        return reservation;
    }

    private Reservation insertReservation(ReservationRequestDTO requestDTO) {
        Reservation reservation = null;
        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(requestDTO.getDate()));
            ps.setTime(2, Time.valueOf(requestDTO.getTime()));
            ps.setString(3, requestDTO.getName());
            ps.setString(4, THEME_NAME);
            ps.setString(5, THEME_DESC);
            ps.setInt(6, THEME_PRICE);
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                reservation = getReservation(resultSet, requestDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

    private void validate(ReservationRequestDTO requestDTO) {
        // 드라이버 연결
        findReservationByDateAndTime(requestDTO);

    }

    private void findReservationByDateAndTime(ReservationRequestDTO requestDTO) {
        try {
            String sql = "SELECT * FROM RESERVATION WHERE DATE = ? AND TIME = ?";
            PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(requestDTO.getDate()));
            ps.setTime(2, Time.valueOf(requestDTO.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                throw new ConflictException("이미 존재하는 예약입니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation findById(Long id) {
        Reservation reservation = findReservationById(id);
        return reservation;
    }

    private Reservation findReservationById(Long id) {
        Reservation reservation = null;
        try {
            String sql = "SELECT * FROM RESERVATION WHERE ID = ?";
            PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reservation = getReservation(rs);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

    public void deleteById(Long id) {
        deleteReservationById(id);
    }

    private void deleteReservationById(Long id) {
        try {
            String sql = "DELETE FROM RESERVATION WHERE ID = ?";
            PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Reservation getReservation(ResultSet rs) throws SQLException {
        return new Reservation(rs.getLong("id"), rs.getDate("date").toLocalDate(), rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                new Theme(rs.getString("theme_name"), rs.getString("theme_desc"), rs.getInt("theme_price")));
    }

    private Reservation getReservation(ResultSet rs, ReservationRequestDTO dto) throws SQLException {
        return new Reservation(rs.getLong(1), dto.getDate(), dto.getTime(), dto.getName(),
                new Theme(THEME_NAME, THEME_DESC, THEME_PRICE));
    }


    public void releaseConnection() {
        connectionHandler.release();
    }
}