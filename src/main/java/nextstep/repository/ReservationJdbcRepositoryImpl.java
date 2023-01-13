package nextstep.repository;

import static nextstep.entity.ThemeConstants.THEME_DESC;
import static nextstep.entity.ThemeConstants.THEME_NAME;
import static nextstep.entity.ThemeConstants.THEME_PRICE;

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
    public Reservation save(ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = null;
        try {
            String sql = ReservationJdbcSql.INSERT_INTO;
            PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservationRequestDTO.getDate()));
            ps.setTime(2, Time.valueOf(reservationRequestDTO.getTime()));
            ps.setString(3, reservationRequestDTO.getName());
            ps.setLong(4, 1L);
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                reservation = makeReservation(resultSet, reservationRequestDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

    @Override
    public boolean existByDateAndTime(LocalDate date, LocalTime time) throws SQLException {
        String sql = ReservationJdbcSql.FIND_BY_DATE_AND_TIME;
        PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        Reservation reservation = null;
        try {
            String sql = ReservationJdbcSql.FIND_BY_ID;
            PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reservation = makeReservation(rs);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(reservation == null){
            return Optional.empty();
        }
        return Optional.of(reservation);
    }

    @Override
    public int deleteById(Long id) {
        try {
            String sql = ReservationJdbcSql.DELETE_BY_ID;
            PreparedStatement ps = connectionHandler.createPreparedStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Reservation makeReservation(ResultSet rs) throws SQLException {
        return new Reservation(rs.getLong("id"), rs.getDate("date").toLocalDate(), rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                new Theme(rs.getString("theme_name"), rs.getString("theme_desc"), rs.getInt("theme_price")));
    }

    private Reservation makeReservation(ResultSet rs, ReservationRequestDTO dto) throws SQLException {
        return new Reservation(rs.getLong(1), dto.getDate(), dto.getTime(), dto.getName(),
                new Theme(THEME_NAME, THEME_DESC, THEME_PRICE));
    }


}