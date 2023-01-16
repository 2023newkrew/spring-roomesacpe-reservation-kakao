package nextstep.reservation.repository;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.repository.jdbc.ReservationResultSetParser;
import nextstep.reservation.repository.jdbc.ReservationStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final ReservationStatementCreator statementCreator;

    private final ReservationResultSetParser resultSetParser;

    @Override
    public boolean existsByDateAndTime(Reservation reservation) {
        return Boolean.TRUE.equals(
                jdbcTemplate.query(
                        getExistsByDateAndTimeStatementCreator(reservation),
                        resultSetParser::existsRow
                ));
    }

    private PreparedStatementCreator getExistsByDateAndTimeStatementCreator(Reservation reservation) {
        return connection -> statementCreator.createSelectByDateAndTimeStatement(connection, reservation);
    }

    @Override
    public Reservation insert(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getInsertStatementCreator(reservation), keyHolder);
        reservation.setId(keyHolder.getKeyAs(Long.class));

        return reservation;
    }

    private PreparedStatementCreator getInsertStatementCreator(Reservation reservation) {
        return connection -> statementCreator.createInsertStatement(connection, reservation);
    }

    @Override
    public Reservation getById(Long id) {
        return jdbcTemplate.query(
                getSelectByIdStatementCreator(id),
                resultSetParser::parseReservation
        );
    }

    private PreparedStatementCreator getSelectByIdStatementCreator(Long id) {
        return connection -> statementCreator.createSelectByIdStatement(connection, id);
    }

    @Override
    public boolean deleteById(Long id) {
        int deletedRow = jdbcTemplate.update(getDeleteByIdStatementCreator(id));

        return deletedRow > 0;
    }

    private PreparedStatementCreator getDeleteByIdStatementCreator(Long id) {
        return connection -> statementCreator.createDeleteByIdStatement(connection, id);
    }
}