package roomescape.reservation.repository.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import roomescape.entity.Reservation;

public class ReservationDaoWithoutTemplateImpl implements ReservationDao {
    public ReservationDaoWithoutTemplateImpl() {
        String dropSql = "DROP TABLE RESERVATION IF EXISTS";
        String createSql =  "CREATE TABLE RESERVATION(" +
                "    id          bigint not null auto_increment,\n" +
                "    date        date,\n" +
                "    time        time,\n" +
                "    name        varchar(20),\n" +
                "    theme_id    bigint not null,\n" +
                "    primary key (id)\n)";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement dropPstmt = con.prepareStatement(dropSql);
             PreparedStatement createPstmt = con.prepareStatement(createSql)
        ){
            dropPstmt.executeUpdate();
            createPstmt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Long create(Reservation reservation) {
        String sql = "INSERT INTO RESERVATION(date, time, name, theme_id) VALUES (?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, reservation.getDate().toString());
            pstmt.setString(2, reservation.getTime().toString());
            pstmt.setString(3, reservation.getName());
            pstmt.setString(4, String.valueOf(reservation.getThemeId()));
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Reservation> selectById(Long id) {
        String sql = "SELECT * FROM RESERVATION WHERE id = ?";
        Reservation reservation = null;
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                reservation = Reservation.builder()
                        .id(rs.getLong("id"))
                        .date(rs.getDate("date").toLocalDate())
                        .time(rs.getTime("time").toLocalTime())
                        .name(rs.getString("name"))
                        .themeId(rs.getLong("theme_id"))
                        .build();
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(reservation);
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public boolean isReservationDuplicated(Reservation reservation) {
        String sql = "select count(*) from RESERVATION WHERE date =  ? and time = ?";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, reservation.getDate().toString());
            pstmt.setString(2, reservation.getTime().toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }
}
