package nextstep.domain.reservation.repository;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.repository.executor.PrepareStatementExecutor;
import nextstep.domain.reservation.repository.executor.PrepareStatementResultSetExecutor;
import nextstep.error.ApplicationException;
import nextstep.utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static nextstep.domain.QuerySetting.Reservation.*;
import static nextstep.error.ErrorType.INTERNAL_SERVER_ERROR;

public class JdbcReservationRepository implements ReservationRepository {

    @Override
    public Reservation save(Reservation reservation) {
        return execute(INSERT, pstmt -> {
            pstmt.setDate(1, Date.valueOf(reservation.getDate()));
            pstmt.setTime(2, Time.valueOf(reservation.getTime()));
            pstmt.setString(3, reservation.getName());
            pstmt.setString(4, reservation.getTheme().getName());
            pstmt.setString(5, reservation.getTheme().getDesc());
            pstmt.setInt(6, reservation.getTheme().getPrice());
            pstmt.executeUpdate();

            return new Reservation(getGeneratedKey(pstmt), reservation);
        });
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return execute(SELECT_BY_ID, (pstmt, rs) -> {
            pstmt.setLong(1, reservationId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Reservation(
                        rs.getLong("id"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time")),
                        rs.getString("name"),
                        new Theme(
                                rs.getString("theme_name"),
                                rs.getString("theme_desc"),
                                rs.getInt("theme_price")
                        )
                ));
            }

            return Optional.empty();
        });
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return execute(SELECT_COUNT_BY_DATE_AND_TIME, (pstmt, rs) -> {
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setTime(2, Time.valueOf(time));
            rs = pstmt.executeQuery();

            rs.next();
            return rs.getInt(1) > 0;
        });
    }

    @Override
    public boolean deleteById(Long reservationId) {
        return execute(DELETE_BY_ID, pstmt -> {
            pstmt.setLong(1, reservationId);

            return pstmt.executeUpdate() > 0;
        });
    }

    private <T> T execute(String query, PrepareStatementExecutor<T> executor) {
        try (Connection conn = JdbcUtils.getConnection();
            PreparedStatement pstmt = query.equals(INSERT) ? conn.prepareStatement(query, RETURN_GENERATED_KEYS) : conn.prepareStatement(query)) {
            return executor.execute(pstmt);
        } catch (SQLException e) {
            throw new ApplicationException(INTERNAL_SERVER_ERROR);
        }
    }

    private <T> T execute(String query, PrepareStatementResultSetExecutor<T> executor) {
        ResultSet rs = null;

        try (Connection conn = JdbcUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            return executor.execute(pstmt, rs);
        } catch (SQLException e) {
            throw new ApplicationException(INTERNAL_SERVER_ERROR);
        } finally {
            JdbcUtils.close(rs);
        }
    }

    private Long getGeneratedKey(PreparedStatement pstmt) throws SQLException {
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new ApplicationException(INTERNAL_SERVER_ERROR);
        }

        return generatedKeys.getLong(1);
    }

}
