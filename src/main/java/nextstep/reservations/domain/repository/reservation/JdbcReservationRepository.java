package nextstep.reservations.domain.repository.reservation;

import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.exceptions.reservation.exception.DuplicateReservationException;
import nextstep.reservations.exceptions.reservation.exception.NoSuchReservationException;
import nextstep.reservations.util.jdbc.JdbcUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcReservationRepository implements ReservationRepository{
    @Override
    public Long add(Reservation reservation) throws DuplicateKeyException, NoSuchReservationException {
        try (Connection connection = JdbcUtil.getConnection()) {
            PreparedStatement pstmt = getInsertOnePstmt(connection, reservation);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong("id");
            }
        }
        catch (SQLException e) {
            throw new DuplicateReservationException();
        }

        return null;
    }

    @Override
    public Reservation findById(Long id) {
        try (Connection connection = JdbcUtil.getConnection()) {
            PreparedStatement pstmt;

            pstmt = connection.prepareStatement(ReservationQuery.FIND_BY_ID.get());
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
            throw new RuntimeException();
        }
    }

    @Override
    public void remove(final Long id) {
        try (Connection connection = JdbcUtil.getConnection()) {
            PreparedStatement pstmt;

            pstmt = connection.prepareStatement(ReservationQuery.REMOVE_BY_ID.get());
            pstmt.setLong(1, id);
            int count = pstmt.executeUpdate();

            if (count == 0) throw new NoSuchReservationException();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
