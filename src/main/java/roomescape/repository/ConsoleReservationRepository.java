package roomescape.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import roomescape.entity.Reservation;
import roomescape.exceptions.exception.DuplicatedReservationException;

public class ConsoleReservationRepository implements ReservationRepository{
    public ConsoleReservationRepository(){
        String dropSql = "DROP TABLE RESERVATION IF EXISTS";
        String createSql =  "CREATE TABLE RESERVATION(" +
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
        ){
            dropPstmt.executeUpdate();
            createPstmt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public Long save(Reservation reservation) {
        if (isReservationIdDuplicated(reservation)) {
            throw new DuplicatedReservationException();
        }
        String sql = "INSERT INTO RESERVATION(date, time, name) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
                PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, reservation.getDate().toString());
            pstmt.setString(2, reservation.getTime().toString());
            pstmt.setString(3, reservation.getName());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()){
                return rs.getLong("id");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT * FROM RESERVATION WHERE id = ?";
        Reservation reservation = null;
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            reservation = new Reservation();
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                reservation.setTime(rs.getTime("time").toLocalTime());
                reservation.setDate(rs.getDate("date").toLocalDate());
                reservation.setName(rs.getString("name"));
                reservation.setId(rs.getLong("id"));
//                reservation.setTheme(
//                        new Theme(rs.getString("theme_name"),
//                                rs.getString("theme_desc"),
//                                rs.getInt("theme_price"))
//                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(reservation);
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setLong(1, id);
            return pstmt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    private boolean isReservationIdDuplicated(Reservation reservation) {
        String sql = "select count(*) from RESERVATION WHERE date =  ? and time = ?";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, reservation.getDate().toString());
            pstmt.setString(2, reservation.getTime().toString());
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }
}
