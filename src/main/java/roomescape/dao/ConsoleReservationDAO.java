package roomescape.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.jdbc.core.PreparedStatementCreator;
import roomescape.dao.preparedstatementcreator.AddReservationPreparedStatementCreator;
import roomescape.dao.preparedstatementcreator.DeleteReservationPreparedStatementCreator;
import roomescape.dao.preparedstatementcreator.ExistReservationPreparedStatementCreator;
import roomescape.dao.preparedstatementcreator.FindReservationPreparedStatementCreator;
import roomescape.dto.Reservation;
import roomescape.exception.BadRequestException;

public class ConsoleReservationDAO extends ReservationDAO {

    private final String url;
    private final String user;
    private final String password;

    public ConsoleReservationDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection openConnection() {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        return con;
    }

    private void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
        }
    }

    private void validateResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new SQLException();
        }
    }

    private PreparedStatement createAddReservationPreparedStatement(
            Connection con, Reservation reservation) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new AddReservationPreparedStatementCreator(reservation);
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private PreparedStatement createFindReservationPreparedStatement(
            Connection con, Long id) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new FindReservationPreparedStatementCreator(id);
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private PreparedStatement createDeleteReservationPreparedStatement(
            Connection con, Long id) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new DeleteReservationPreparedStatementCreator(id);
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private PreparedStatement createExistReservationPreparedStatement(
            Connection con, LocalDate date, LocalTime time) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new ExistReservationPreparedStatementCreator(date, time);
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private Long getGeneratedKey(PreparedStatement ps) throws SQLException {
        ResultSet resultSet = ps.getGeneratedKeys();
        validateResultSet(resultSet);
        return resultSet.getLong(1);
    }

    private Reservation parseFindResultSet(ResultSet resultSet) throws SQLException {
        validateResultSet(resultSet);
        return getReservationRowMapper().mapRow(resultSet, 1);
    }

    private boolean parseExistResultSet(ResultSet resultSet) throws SQLException {
        validateResultSet(resultSet);
        return resultSet.getBoolean("result");
    }

    private Long executeAddConnection(Connection con, Reservation reservation) {
        try {
            PreparedStatement ps = createAddReservationPreparedStatement(con, reservation);
            ps.executeUpdate();
            return getGeneratedKey(ps);
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    private Reservation executeFindConnection(Connection con, Long id) {
        try {
            PreparedStatement ps = createFindReservationPreparedStatement(con, id);
            ResultSet resultSet = ps.executeQuery();
            return parseFindResultSet(resultSet);
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    private void executeDeleteConnection(Connection con, Long id) {
        try {
            PreparedStatement ps = createDeleteReservationPreparedStatement(con, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    private boolean executeExistConnection(Connection con, LocalDate date, LocalTime time) {
        try {
            PreparedStatement ps = createExistReservationPreparedStatement(con, date, time);
            ResultSet resultSet = ps.executeQuery();
            return parseExistResultSet(resultSet);
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    @Override
    protected boolean existReservation(LocalDate date, LocalTime time) {
        Connection con = openConnection();
        boolean result = executeExistConnection(con, date, time);
        closeConnection(con);
        return result;
    }

    @Override
    public Long addReservation(Reservation reservation) {
        validateReservation(reservation);
        Connection con = openConnection();
        Long id = executeAddConnection(con, reservation);
        closeConnection(con);
        return id;
    }

    @Override
    public Reservation findReservation(Long id) {
        Connection con = openConnection();
        Reservation reservation = executeFindConnection(con, id);
        closeConnection(con);
        return reservation;
    }

    @Override
    public void deleteReservation(Long id) {
        Connection con = openConnection();
        executeDeleteConnection(con, id);
        closeConnection(con);
    }
}
