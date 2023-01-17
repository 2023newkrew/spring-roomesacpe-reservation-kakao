package nextstep.repository;

import static nextstep.repository.ReservationJdbcSql.DELETE_BY_ID_STATEMENT;
import static nextstep.repository.ReservationJdbcSql.FIND_BY_DATE_AND_TIME_STATEMENT;
import static nextstep.repository.ReservationJdbcSql.FIND_BY_ID_STATEMENT;
import static nextstep.repository.ReservationJdbcSql.EXIST_BY_THEME_ID_STATEMENT;
import static nextstep.repository.ReservationJdbcSql.INSERT_INTO_STATEMENT;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import nextstep.console.utils.ConnectionHandler;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;

public class ReservationJdbcRepositoryImpl implements ReservationRepository {

    private final ConnectionHandler connectionHandler;

    public ReservationJdbcRepositoryImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public Reservation save(Reservation reservation) {
        PreparedStatement ps;
        ResultSet rs;
        Reservation entity = null;
        try {
            ps = connectionHandler.createPreparedStatement(INSERT_INTO_STATEMENT, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme().getId());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity = Reservation.creteReservation(reservation, rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releaseResultSet(rs);
        releasePreparedStatement(ps);
        return entity;
    }

    @Override
    public boolean existByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId) {
        PreparedStatement ps;
        ResultSet rs;
        boolean exist;
        try {
            ps = connectionHandler.createPreparedStatement(FIND_BY_DATE_AND_TIME_STATEMENT, new String[]{"id"});
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
            ps = connectionHandler.createPreparedStatement(FIND_BY_ID_STATEMENT, new String[]{"id"});
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
        return executeSqlReturnRow(id, DELETE_BY_ID_STATEMENT);
    }

    @Override
    public boolean existByThemeId(Long id) {

        PreparedStatement ps;
        ResultSet rs;
        boolean exist;
        try {
            ps = connectionHandler.createPreparedStatement(EXIST_BY_THEME_ID_STATEMENT, new String[]{"id"});
            ps.setLong(1, id);
            rs = ps.executeQuery();
            exist = rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releasePreparedStatementAndResultSet(ps, rs);
        return exist;
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
        Theme theme = Theme.builder()
                .description(rs.getString("theme_desc"))
                .name(rs.getString("theme_name"))
                .price(rs.getInt("price")).build();
        Reservation reservation = Reservation.builder()
                .theme(theme)
                .date(rs.getDate("date").toLocalDate())
                .time(rs.getTime("time").toLocalTime())
                .name(rs.getString("name"))
                .build();
        return Reservation.creteReservation(reservation, rs.getLong("id"));
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