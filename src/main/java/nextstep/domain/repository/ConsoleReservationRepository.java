package nextstep.domain.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ConsoleReservationRepository implements ReservationRepository {

    private static final String INSERT_SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM reservation WHERE id = ?";
    private static final String SELECT_COUNT_BY_DATE_AND_TIME = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";

    @Override
    public Reservation save(Reservation reservation) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(INSERT_SQL, new String[] {"id"});

            pstmt.setDate(1, Date.valueOf(reservation.getDate()));
            pstmt.setTime(2, Time.valueOf(reservation.getTime()));
            pstmt.setString(3, reservation.getName());
            pstmt.setString(4, reservation.getTheme().getName());
            pstmt.setString(5, reservation.getTheme().getDesc());
            pstmt.setInt(6, reservation.getTheme().getPrice());
            pstmt.executeUpdate();

            return new Reservation(getGeneratedKey(pstmt), reservation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.close(pstmt, conn);
        }
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(SELECT_BY_ID_SQL);
            pstmt.setLong(1, reservationId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getLong("id"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time")),
                        rs.getString("name"),
                        new Theme(
                                rs.getString("theme_name"),
                                rs.getString("theme_desc"),
                                rs.getInt("theme_price")
                        )
                );
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(SELECT_COUNT_BY_DATE_AND_TIME);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setTime(2, Time.valueOf(time));
            ResultSet rs = pstmt.executeQuery();

            return rs.getInt(1) > 0;   
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long reservationId) {

    }

    @Override
    public void deleteAll() {

    }

    private Long getGeneratedKey(PreparedStatement pstmt) throws SQLException {
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("예약 생성에 실패하였습니다.");
        }

        return generatedKeys.getLong(1);
    }

}
