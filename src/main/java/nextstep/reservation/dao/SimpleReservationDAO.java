package nextstep.reservation.dao;

import lombok.AllArgsConstructor;
import nextstep.etc.util.ResultSetParser;
import nextstep.etc.util.StatementCreator;
import nextstep.reservation.dto.ReservationDTO;

import java.sql.*;

@AllArgsConstructor
public class SimpleReservationDAO implements ReservationDAO {

    private static final String URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";

    private static final String USER = "sa";

    private static final String PASSWORD = "";

    @FunctionalInterface
    private interface QueryFunction<R> {
        R query(Connection connection) throws SQLException;
    }

    @Override
    public Boolean existsByDateAndTime(Date date, Time time) {
        return tryQuery(getExistsByDateAndTimeQuery(date, time));
    }

    private <R> R tryQuery(QueryFunction<R> func) {
        try (Connection connection = getConnection()) {
            return func.query(connection);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private QueryFunction<Boolean> getExistsByDateAndTimeQuery(Date date, Time time) {
        return connection -> {
            var ps = StatementCreator.createSelectByDateAndTimeStatement(connection, date, time);
            ps.executeUpdate();
            var rs = ps.getGeneratedKeys();
            rs.next();
            return ResultSetParser.existsRow(rs);
        };
    }

    @Override
    public Long insert(ReservationDTO dto) {
        return tryQuery(getInsertQuery(dto));
    }

    private QueryFunction<Long> getInsertQuery(ReservationDTO dto) {
        return connection -> {
            var ps = StatementCreator.createInsertStatement(connection, dto);
            ps.executeUpdate();
            var rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        };
    }

    @Override
    public ReservationDTO getById(Long id) {
        return tryQuery(getSelectByIdQuery(id));
    }

    private QueryFunction<ReservationDTO> getSelectByIdQuery(Long id) {
        return connection -> {
            var ps = StatementCreator.createSelectByIdStatement(connection, id);
            var rs = ps.executeQuery();
            rs.next();
            return ResultSetParser.parseReservationDto(rs);
        };
    }

    @Override
    public Boolean deleteById(Long id) {
        return tryQuery(getDeleteByIdQuery(id));
    }

    private QueryFunction<Boolean> getDeleteByIdQuery(Long id) {
        return connection -> {
            var ps = StatementCreator.createDeleteByIdStatement(connection, id);
            var rs = ps.executeUpdate();
            return rs > 0;
        };
    }
}