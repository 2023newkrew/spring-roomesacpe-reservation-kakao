package roomescape.reservation.dao;

import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoConsole implements ReservationDao {

    private Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    private void close(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }

    public Reservation addReservation(Reservation reservation) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(insert, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            //ps.setString(4, reservation.getTheme().getName());
            //ps.setString(5, reservation.getTheme().getDesc());
            //ps.setInt(6, reservation.getTheme().getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            reservation.setId(rs.getLong("id"));
            return reservation;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
    }

    public List<Reservation> findReservationById(Long id) {
        List<Reservation> reservations = new ArrayList<>();
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(selectById);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reservations.add(new ReservationMapper().mapRow(rs, rs.getRow()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
        return reservations;
    }

    public List<Reservation> findReservationByDateAndTime(String date, String time) {
        List<Reservation> reservations = new ArrayList<>();
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(selectByDateAndTime);
            ps.setDate(1, Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ISO_DATE)));
            ps.setTime(2, Time.valueOf(LocalTime.parse(time)));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reservations.add(new ReservationMapper().mapRow(rs, rs.getRow()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
        return reservations;
    }

    public void removeReservation(Long id) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(deleteById);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
    }
}