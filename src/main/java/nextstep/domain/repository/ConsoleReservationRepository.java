package nextstep.domain.repository;

import nextstep.domain.Reservation;
import nextstep.utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ConsoleReservationRepository implements ReservationRepository {

    private static final String INSERT_SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";

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
        return Optional.empty();
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return false;
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
