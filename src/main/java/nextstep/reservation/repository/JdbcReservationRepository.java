package nextstep.reservation.repository;

import lombok.RequiredArgsConstructor;
import nextstep.etc.util.ResultSetParser;
import nextstep.etc.util.StatementCreator;
import nextstep.reservation.domain.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean existsByDateAndTime(Reservation reservation) {
        return Boolean.TRUE.equals(
                jdbcTemplate.query(
                        getExistsByDateAndTimeStatementCreator(reservation),
                        ResultSetParser::existsRow
                ));
    }

    private static PreparedStatementCreator getExistsByDateAndTimeStatementCreator(Reservation reservation) {
        return con -> StatementCreator.createSelectByDateAndTimeStatement(con, reservation);
    }

    @Override
    public Long insert(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getInsertStatementCreator(reservation), keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    private static PreparedStatementCreator getInsertStatementCreator(Reservation reservation) {
        return con -> StatementCreator.createInsertStatement(con, reservation);
    }

    @Override
    public Reservation getById(Long id) {
        return jdbcTemplate.query(
                getSelectByIdStatementCreator(id),
                ResultSetParser::parseReservation
        );
    }

    private static PreparedStatementCreator getSelectByIdStatementCreator(Long id) {
        return con -> StatementCreator.createSelectByIdStatement(con, id);
    }

    @Override
    public boolean deleteById(Long id) {
        int deletedRow = jdbcTemplate.update(getDeleteByIdStatementCreator(id));

        return deletedRow > 0;
    }

    private static PreparedStatementCreator getDeleteByIdStatementCreator(Long id) {
        return con -> StatementCreator.createDeleteByIdStatement(con, id);
    }
}