package roomservice.repository;

import roomservice.domain.entity.Reservation;
import roomservice.domain.entity.Theme;
import roomservice.exceptions.exception.DuplicatedReservationException;
import roomservice.exceptions.exception.NonExistentReservationException;

import java.sql.*;

/**
 * ReservationConsoleDao implements ReservationDao using spring-jdbc.<br>
 * Database is always cleared when you start console program.
 * This class is not added to spring.
 */
public class ReservationConsoleDao implements ReservationDao {
    public ReservationConsoleDao() {
        String dropSql = "DROP TABLE RESERVATION IF EXISTS";
        String createSql = "CREATE TABLE RESERVATION(" +
                "    id          bigint not null auto_increment,\n" +
                "    date        date,\n" +
                "    time        time,\n" +
                "    name        varchar(20),\n" +
                "    theme_name  varchar(20),\n" +
                "    theme_desc  varchar(255),\n" +
                "    theme_price int,\n" +
                "    primary key (id)\n)";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement dropPstmt = con.prepareStatement(dropSql);
             PreparedStatement createPstmt = con.prepareStatement(createSql)
        ) {
            dropPstmt.executeUpdate();
            createPstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long insertReservation(Reservation reservation) {
        validateDuplication(reservation);
        String sql = "INSERT INTO RESERVATION(date, time, name) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, reservation.getDate().toString());
            pstmt.setString(2, reservation.getTime().toString());
            pstmt.setString(3, reservation.getName());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public Reservation selectReservation(long id) {
        String sql = "SELECT * FROM RESERVATION WHERE id = ?";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            Reservation reservation;
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                reservation = new Reservation(
                        rs.getLong("id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(),
                        rs.getString("name"),
                        new Theme(0L,
                                rs.getString("theme_name"),
                                rs.getString("theme_desc"),
                                rs.getInt("theme_price"))
                );
                return reservation;
            }
            throw new NonExistentReservationException();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteReservation(long id) {
        String sql = "DELETE FROM RESERVATION WHERE id = ?";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            if (pstmt.executeUpdate() == 0) {
                throw new NonExistentReservationException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void validateDuplication(Reservation reservation) {
        String sql = "select * from RESERVATION WHERE date =  ? and time = ?";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, reservation.getDate().toString());
            pstmt.setString(2, reservation.getTime().toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                throw new DuplicatedReservationException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
