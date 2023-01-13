package nextstep.repository;

import static nextstep.repository.ReservationJdbcSql.DELETE_BY_ID;
import static nextstep.repository.ReservationJdbcSql.FIND_BY_DATE_AND_TIME;
import static nextstep.repository.ReservationJdbcSql.FIND_BY_ID;
import static nextstep.repository.ReservationJdbcSql.FIND_BY_THEME_ID;
import static nextstep.repository.ReservationJdbcSql.INSERT_INTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import nextstep.dto.ConnectionHandler;
import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;

public class ReservationJdbcRepositoryImpl implements ReservationRepository {

    private final ConnectionHandler connectionHandler;

    public ReservationJdbcRepositoryImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public Long save(ReservationRequestDTO reservationRequestDTO) {
        Long id = null;
        PreparedStatement ps;
        ResultSet rs;
        try {
            String sql = INSERT_INTO;
            ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservationRequestDTO.getDate()));
            ps.setTime(2, Time.valueOf(reservationRequestDTO.getTime()));
            ps.setString(3, reservationRequestDTO.getName());
            ps.setLong(4, reservationRequestDTO.getThemeId());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releaseResultSet(rs);
        releasePreparedStatement(ps);
        return id;
    }

    @Override
    public boolean existByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId) {
        String sql = FIND_BY_DATE_AND_TIME;
        PreparedStatement ps;
        ResultSet rs;
        boolean exist;
        try {
            ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ps.setLong(3, themeId);
            rs = ps.executeQuery();
            exist = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releasePreparedStatementAndResultSet(ps, rs);

        return exist;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        Optional<Reservation> reservation = Optional.empty();
        PreparedStatement ps;
        try {
            ps = connectionHandler.createPreparedStatement(FIND_BY_ID, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reservation = Optional.of(makeReservation(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releasePreparedStatement(ps);
        return reservation;
    }

    @Override
    public int deleteById(Long id) {
        return executeSqlReturnRow(id, DELETE_BY_ID);
    }

    @Override
    public int existByThemeId(Long id) {
        return executeSqlReturnRow(id, FIND_BY_THEME_ID);
    }

    private int executeSqlReturnRow(Long id, String findByThemeId) {
        PreparedStatement ps;
        int row;
        try {
            ps = connectionHandler.createPreparedStatement(findByThemeId, new String[]{"id"});
            ps.setLong(1, id);
            row = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releasePreparedStatement(ps);
        return row;
    }

    private Reservation makeReservation(ResultSet rs) throws SQLException {
        return new Reservation(rs.getLong("id"), rs.getDate("date").toLocalDate(), rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                new Theme(rs.getString("theme_name"), rs.getString("theme_desc"), rs.getInt("theme_price")));
    }

    private static void releasePreparedStatementAndResultSet(PreparedStatement ps, ResultSet rs) {
        releaseResultSet(rs);

        releasePreparedStatement(ps);
    }

    private static void releasePreparedStatement(PreparedStatement ps) {
        try {
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void releaseResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}