package nextstep.reservations.domain.repository.reservation;

import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.exceptions.reservation.exception.DuplicateReservationException;
import nextstep.reservations.util.jdbc.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsoleReservationRepository implements ReservationRepository{
    public ConsoleReservationRepository() {
        try (Connection connection = JdbcUtil.getConnection()) {
            PreparedStatement pstmt;

            pstmt = connection.prepareStatement(ReservationQuery.TRUNCATE.get());
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Long add(Reservation reservation) {
        try (Connection connection = JdbcUtil.getConnection()) {
            PreparedStatement pstmt = getInsertOnePstmt(connection, reservation);
            pstmt.executeUpdate();
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
            return null;
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
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
