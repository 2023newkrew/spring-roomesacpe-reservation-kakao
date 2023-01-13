package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.exception.ErrorCode.*;

public class ConsoleReservationRepository extends ReservationRepository {

    private static final String CONNECTION_SUCCESS_MESSAGE = "정상적으로 연결되었습니다.";
    private static final String DATABASE_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    private static final String DATABASE_USER_ID = "sa";
    private static final String DATABASE_USER_PASSWORD = "";

    private Connection con = null;

    public ConsoleReservationRepository() {
        connect();
    }

    private void connect() {
        // 드라이버 연결
        try {
            con = DriverManager.getConnection(DATABASE_URL, DATABASE_USER_ID, DATABASE_USER_PASSWORD);
            System.out.println(CONNECTION_SUCCESS_MESSAGE);
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
        }
    }

    @Override
    public Long save(LocalDate date, LocalTime time, String name, Theme theme) {
        try {
            validateReservation(date, time);

            PreparedStatement ps = getReservationPreparedStatement(con, date, time, name, theme);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong("id");
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
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
                throw new ReservationException(DUPLICATED_RESERVATION_EXISTS);
            }
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
        }
    }

    @Override
    public Long save(Reservation reservation) {
        return this.save(reservation.getDate(), reservation.getTime(),
                reservation.getName(), reservation.getTheme());
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
            throw new ReservationException(RESERVATION_NOT_FOUND);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(DELETE_BY_ID_SQL, new String[]{"id"});
            ps.setLong(1, id);
            int updatedRows = ps.executeUpdate();
            if (updatedRows == 0) {
                throw new ReservationException(RESERVATION_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
        }
    }

    @Override
    public void createTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
        }
    }

    @Override
    public void dropTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(DROP_TABLE_SQL);
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
        }
    }
}
