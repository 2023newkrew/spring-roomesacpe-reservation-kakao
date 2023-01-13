package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.domain.repository.ReservationRepository;
import nextstep.exception.JdbcException;
import nextstep.utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsoleReservationRepository implements ReservationRepository {

    @Override
    public Reservation save(Reservation reservation) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Reservation.INSERT_SQL, new String[] {"id"});

            pstmt.setDate(1, Date.valueOf(reservation.getDate()));
            pstmt.setTime(2, Time.valueOf(reservation.getTime()));
            pstmt.setString(3, reservation.getName());
            pstmt.setLong(4, reservation.getThemeId());
            pstmt.executeUpdate();

            return new Reservation(getGeneratedKey(pstmt), reservation);
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage());
        } finally {
            JdbcUtils.close(pstmt, conn);
        }
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Reservation.SELECT_BY_ID_SQL);
            pstmt.setLong(1, reservationId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getLong("id"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time")),
                        rs.getString("name"),
                        rs.getLong("theme_id")
                );
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            JdbcUtils.close(rs, pstmt, conn);
        }

        return Optional.empty();
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Reservation> reservations = new ArrayList<>();

        try{
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Reservation.SELECT_BY_THEME_ID_SQL);
            pstmt.setLong(1, themeId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getLong("id"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time")),
                        rs.getString("name"),
                        rs.getLong("theme_id")
                );
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            JdbcUtils.close(rs, pstmt, conn);
        }
        return reservations;
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Reservation.SELECT_COUNT_BY_DATE_AND_TIME_SQL);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setTime(2, Time.valueOf(time));
            rs = pstmt.executeQuery();

            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage());
        } finally {
            JdbcUtils.close(rs, pstmt, conn);
        }
    }

    @Override
    public boolean deleteById(Long reservationId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Reservation.DELETE_BY_ID_SQL);
            pstmt.setLong(1, reservationId);

            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage());
        } finally {
            JdbcUtils.close(pstmt, conn);
        }
    }

    @Override
    public void deleteAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JdbcUtils.getConnection();
            pstmt = conn.prepareStatement(Queries.Reservation.DELETE_ALL_SQL);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage());
        } finally {
            JdbcUtils.close(pstmt, conn);
        }
    }

    private Long getGeneratedKey(PreparedStatement pstmt) throws SQLException {
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new JdbcException("id 값이 존재하지 않습니다.");
        }

        return generatedKeys.getLong(1);
    }

}
