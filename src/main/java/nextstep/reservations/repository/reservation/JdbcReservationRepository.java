package nextstep.reservations.repository.reservation;

import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.exceptions.reservation.exception.DuplicateReservationException;
import nextstep.reservations.exceptions.reservation.exception.NoSuchReservationException;
import nextstep.reservations.exceptions.theme.exception.NoSuchThemeException;
import nextstep.reservations.util.jdbc.JdbcUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Repository
@Primary
public class JdbcReservationRepository implements ReservationRepository{
    private static final String INSERT_ONE_QUERY = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM reservation WHERE id = ?";
    private static final String REMOVE_BY_ID_QUERY = "DELETE FROM reservation WHERE id = ?";
    public static final int DuplicateReservationError = 23505;
    public static final int NoSuchThemeError = 23506;
    private final SQLExceptionTranslator sqlExceptionTranslator;

    public JdbcReservationRepository() {
        this.sqlExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator();
    }

    @Override
    public Long add(Reservation reservation) throws DuplicateKeyException, NoSuchReservationException {
        try (Connection connection = JdbcUtil.getConnection()) {
            PreparedStatement pstmt = getInsertOnePstmt(connection, reservation, INSERT_ONE_QUERY);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong("id");
            }
        }
        catch (SQLException e) {
            if (e.getErrorCode() == DuplicateReservationError) {
                throw new DuplicateReservationException();
            }
            else if (e.getErrorCode() == NoSuchThemeError) {
                throw new NoSuchThemeException();
            }
        }

        return null;
    }

    @Override
    public Reservation findById(Long id) {
        try (Connection connection = JdbcUtil.getConnection()) {
            PreparedStatement pstmt;

            pstmt = connection.prepareStatement(FIND_BY_ID_QUERY);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Reservation.builder()
                        .id(rs.getLong("id"))
                        .date(rs.getDate("date").toLocalDate())
                        .time(rs.getTime("time").toLocalTime())
                        .name(rs.getString("name"))
                        .theme(Theme.builder()
                                .name(rs.getString("theme_name"))
                                .desc(rs.getString("theme_desc"))
                                .price(rs.getInt("theme_price"))
                                .build())
                        .build();
            }
            else {
                throw new NoSuchReservationException();
            }
        }
        catch (SQLException e) {
            throw Objects.requireNonNull(sqlExceptionTranslator.translate("find", FIND_BY_ID_QUERY, e));
        }
    }

    @Override
    public int remove(final Long id) {
        try (Connection connection = JdbcUtil.getConnection()) {
            PreparedStatement pstmt;

            pstmt = connection.prepareStatement(REMOVE_BY_ID_QUERY);
            pstmt.setLong(1, id);
            return pstmt.executeUpdate();
        }
        catch (SQLException e) {
            throw Objects.requireNonNull(sqlExceptionTranslator.translate("remove", REMOVE_BY_ID_QUERY, e));
        }
    }
}
