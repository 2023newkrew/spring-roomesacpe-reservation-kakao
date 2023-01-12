package nextstep.reservation.dao;

import lombok.AllArgsConstructor;
import nextstep.etc.util.ResultSetParser;
import nextstep.etc.util.StatementCreator;
import nextstep.reservation.dto.ReservationDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@Component
public class JdbcReservationDAO implements ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean existsByDateAndTime(Date date, Time time) {
        return jdbcTemplate.query(
                getExistsByDateAndTimeStatementCreator(date, time),
                ResultSetParser::existsRow
        );
    }

    private static PreparedStatementCreator getExistsByDateAndTimeStatementCreator(Date date, Time time) {
        return con -> StatementCreator.createSelectByDateAndTimeStatement(con, date, time);
    }

    @Override
    public Long insert(ReservationDTO dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getInsertStatementCreator(dto), keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    private static PreparedStatementCreator getInsertStatementCreator(ReservationDTO dto) {
        return con -> StatementCreator.createInsertStatement(con, dto);
    }

    @Override
    public ReservationDTO getById(Long id) {
        return jdbcTemplate.query(
                getSelectByIdStatementCreator(id),
                ResultSetParser::parseReservationDto
        );
    }

    private static PreparedStatementCreator getSelectByIdStatementCreator(Long id) {
        return con -> StatementCreator.createSelectByIdStatement(con, id);
    }

    @Override
    public Boolean deleteById(Long id) {
        int deletedRow = jdbcTemplate.update(getDeleteByIdStatementCreator(id));

        return deletedRow > 0;
    }

    private static PreparedStatementCreator getDeleteByIdStatementCreator(Long id) {
        return con -> StatementCreator.createDeleteByIdStatement(con, id);
    }
}