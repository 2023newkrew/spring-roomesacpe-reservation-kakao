package nextstep.dao;

import nextstep.dto.ConnectionHandler;
import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import nextstep.exception.ConflictException;

import java.sql.*;

import static nextstep.entity.ThemeConstants.*;

public class ReservationDAO {

    private final ConnectionHandler connectionHandler;

    public ReservationDAO() throws SQLException {
        this.connectionHandler = new ConnectionHandler();
    }

    public Reservation addReservation(ReservationRequestDTO requestDTO) throws SQLException {
        validate(requestDTO);

        return insertReservation(requestDTO);
    }

    private Reservation insertReservation(ReservationRequestDTO requestDTO) throws SQLException {
        Reservation reservation = null;
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

        return reservation;
    }

    private void validate(ReservationRequestDTO requestDTO) throws SQLException {
        findReservationByDateAndTime(requestDTO);
    }

    private void findReservationByDateAndTime(ReservationRequestDTO requestDTO) throws SQLException {
        String sql = "SELECT * FROM RESERVATION WHERE DATE = ? AND TIME = ?";
        PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
        ps.setDate(1, Date.valueOf(requestDTO.getDate()));
        ps.setTime(2, Time.valueOf(requestDTO.getTime()));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            throw new ConflictException("이미 존재하는 예약입니다.");
        }
    }

    public Reservation findById(Long id) {

        return findReservationById(id);
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

    public void deleteById(Long id) throws SQLException {
        deleteReservationById(id);
    }

    private void deleteReservationById(Long id) throws SQLException {
        String sql = "DELETE FROM RESERVATION WHERE ID = ?";
        PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
        ps.setLong(1, id);
        ps.executeUpdate();
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


    public void releaseConnection() throws SQLException {
        connectionHandler.release();
    }
}