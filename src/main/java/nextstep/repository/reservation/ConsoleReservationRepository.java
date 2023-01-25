package nextstep.repository.reservation;

import nextstep.config.DbConfig;
import nextstep.domain.Reservation;
import nextstep.exception.EscapeException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static nextstep.exception.ErrorCode.*;

public class ConsoleReservationRepository extends ReservationRepository {

    private Connection con = null;
    private DbConfig dbConfig = new DbConfig();

    public ConsoleReservationRepository() {
        connect();
    }

    private void connect() {
        // 드라이버 연결
        try {
            con = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }

    @Override
    public Long save(LocalDate date, LocalTime time, String name, Long themeId) {
        try {
            validateReservation(date, time);

            PreparedStatement ps = getReservationPreparedStatement(con, date, time, name, themeId);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong("id");
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }

    private void validateReservation(LocalDate date, LocalTime time) {
        try {
            PreparedStatement ps = con.prepareStatement(CHECK_DUPLICATION_SQL);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));

            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            int row = resultSet.getInt("total_rows");
            if (row > 0) {
                throw new EscapeException(DUPLICATED_RESERVATION_EXISTS);
            }
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }

    @Override
    public Long save(Reservation reservation) {
        return this.save(reservation.getDate(), reservation.getTime(),
                reservation.getName(), reservation.getThemeId());
    }

    @Override
    public Reservation findById(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(FIND_BY_ID_SQL, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return Reservation.from(resultSet);
        } catch (SQLException e) {
            throw new EscapeException(RESERVATION_NOT_FOUND);
        }
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        try {
            PreparedStatement ps = con.prepareStatement(FIND_BY_THEME_ID_SQL, new String[]{"id"});
            ps.setLong(1, themeId);
            ResultSet resultSet = ps.executeQuery();

            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                reservations.add(Reservation.from(resultSet));
            }

            return reservations;
        } catch (SQLException e) {
            throw new EscapeException(RESERVATION_NOT_FOUND);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(DELETE_BY_ID_SQL, new String[]{"id"});
            ps.setLong(1, id);
            int updatedRows = ps.executeUpdate();
            if (updatedRows == 0) {
                throw new EscapeException(RESERVATION_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }

    @Override
    public void createTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }

    @Override
    public void dropTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(DROP_TABLE_SQL);
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }
}
