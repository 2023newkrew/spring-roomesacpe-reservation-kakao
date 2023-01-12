package nextstep.dao;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationSaveForm;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationJdbcApiDAO implements ReservationDAO, JdbcApiDAO {
    @Override
    public Long save(ReservationSaveForm reservationSaveForm) {
        try (Connection con = getConnection()) {
            PreparedStatementCreator insertPreparedStatementCreator = ReservationDAO.getInsertPreparedStatementCreator(reservationSaveForm);
            PreparedStatement ps = insertPreparedStatementCreator.createPreparedStatement(con);

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reservation findById(Long id) {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_BY_ID_SQL);
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return RESERVATION_ROW_MAPPER.mapRow(rs, rs.getRow());
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> findByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId) {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_BY_DATE_TIME_THEME_SQL);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ps.setLong(3, themeId);

            ResultSet rs = ps.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (rs.next()) {
                reservations.add(RESERVATION_ROW_MAPPER.mapRow(rs, rs.getRow()));
            }
            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Long id) {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(DELETE_BY_ID_SQL);
            ps.setLong(1, id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
