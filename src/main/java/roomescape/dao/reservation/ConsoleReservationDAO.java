package roomescape.dao.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.FindReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.InsertReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.RemoveReservationPreparedStatementCreator;
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
        } catch (SQLException e) {
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
            e.printStackTrace();
        }
    }

    private void validateResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new BadRequestException();
        }
    }

    private PreparedStatement createAddReservationPreparedStatement(
            Connection con, Reservation reservation) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new InsertReservationPreparedStatementCreator(reservation);
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
                new RemoveReservationPreparedStatementCreator(id);
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private PreparedStatement createExistReservationPreparedStatement(
            Connection con, Reservation reservation) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new ExistReservationPreparedStatementCreator(reservation);
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private PreparedStatement createExistReservationIdPreparedStatement(
            Connection con, Long id) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new ExistReservationIdPreparedStatementCreator(id);
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private Long getGeneratedKey(PreparedStatement ps) throws SQLException {
        ResultSet resultSet = ps.getGeneratedKeys();
        validateResultSet(resultSet);
        return resultSet.getLong(1);
    }

    private Reservation parseFindResultSet(ResultSet resultSet) throws SQLException {
        validateResultSet(resultSet);
        return getRowMapper().mapRow(resultSet, 1);
    }

    private boolean parseExistResultSet(ResultSet resultSet) throws SQLException {
        validateResultSet(resultSet);
        return resultSet.getBoolean("result");
    }

    private Long executeAddConnection(Connection con, Reservation reservation) throws SQLException {
        PreparedStatement ps = createAddReservationPreparedStatement(con, reservation);
        ps.executeUpdate();
        return getGeneratedKey(ps);
    }

    private Reservation executeFindConnection(Connection con, Long id) throws SQLException {
        PreparedStatement ps = createFindReservationPreparedStatement(con, id);
        ResultSet resultSet = ps.executeQuery();
        return parseFindResultSet(resultSet);
    }

    private void executeDeleteConnection(Connection con, Long id) throws SQLException {
        PreparedStatement ps = createDeleteReservationPreparedStatement(con, id);
        ps.executeUpdate();
    }

    private boolean executeExistConnection(Connection con, Reservation reservation) throws SQLException {
        PreparedStatement ps = createExistReservationPreparedStatement(con, reservation);
        ResultSet resultSet = ps.executeQuery();
        return parseExistResultSet(resultSet);
    }

    private boolean executeExistIdConnection(Connection con, Long id) throws SQLException {
        PreparedStatement ps = createExistReservationIdPreparedStatement(con, id);
        ResultSet resultSet = ps.executeQuery();
        return parseExistResultSet(resultSet);
    }

    @Override
    public boolean exist(Reservation reservation) {
        Connection con = openConnection();
        boolean result;
        try {
            result = executeExistConnection(con, reservation);
        } catch (SQLException e) {
            throw new BadRequestException();
        } finally {
            closeConnection(con);
        }
        return result;
    }

    @Override
    public boolean existId(Long id) {
        Connection con = openConnection();
        boolean result;
        try {
            result = executeExistIdConnection(con, id);
        } catch (SQLException e) {
            throw new BadRequestException();
        } finally {
            closeConnection(con);
        }
        return result;
    }

    @Override
    public Long create(Reservation reservation) {
        Connection con = openConnection();
        Long id;
        try {
            id = executeAddConnection(con, reservation);
        } catch (SQLException e) {
            throw new BadRequestException();
        } finally {
            closeConnection(con);
        }
        return id;
    }

    @Override
    public Reservation find(Long id) {
        Connection con = openConnection();
        Reservation reservation;
        try {
            reservation = executeFindConnection(con, id);
        } catch (SQLException e) {
            throw new BadRequestException();
        } finally {
            closeConnection(con);
        }
        return reservation;
    }

    @Override
    public void remove(Long id) {
        Connection con = openConnection();
        try {
            executeDeleteConnection(con, id);
        } catch (SQLException e) {
            throw new BadRequestException();
        } finally {
            closeConnection(con);
        }
    }
}
