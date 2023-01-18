package roomescape.reservation.repository.console;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.common.AbstractReservationH2Repository;
import roomescape.reservation.repository.common.ReservationMapper;

import java.sql.*;

public class ReservationRepositoryConsole extends AbstractReservationH2Repository {

    private Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            //System.out.println("정상적으로 연결되었습니다.");
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

//    public Reservation addReservation(Reservation reservation) {
//        Connection con = getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement(insert, new String[]{"id"});
//            ps.setDate(1, Date.valueOf(reservation.getDate()));
//            ps.setTime(2, Time.valueOf(reservation.getTime()));
//            ps.setString(3, reservation.getName());
//            //ps.setString(4, reservation.getTheme().getName());
//            //ps.setString(5, reservation.getTheme().getDesc());
//            //ps.setInt(6, reservation.getTheme().getPrice());
//            ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//            rs.next();
//            reservation.setId(rs.getLong("id"));
//            return reservation;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        } finally {
//            close(con);
//        }
//    }
//
//    public List<Reservation> findReservationById(Long id) {
//        List<Reservation> reservations = new ArrayList<>();
//        Connection con = getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement(selectById);
//            ps.setLong(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                reservations.add(new ReservationMapper().mapRow(rs, rs.getRow()));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            close(con);
//        }
//        return reservations;
//    }
//
//    public List<Reservation> findReservationByDateAndTime(String date, String time) {
//        List<Reservation> reservations = new ArrayList<>();
//        Connection con = getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement(selectByDateAndTime);
//            ps.setDate(1, Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ISO_DATE)));
//            ps.setTime(2, Time.valueOf(LocalTime.parse(time)));
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                reservations.add(new ReservationMapper().mapRow(rs, rs.getRow()));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            close(con);
//        }
//        return reservations;
//    }
//
//    public void removeReservation(Long id) {
//        Connection con = getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement(deleteById);
//            ps.setLong(1, id);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            close(con);
//        }
//    }

    @Override
    public Reservation add(Reservation reservation) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(insertQuery, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getThemeId());
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

    @Override
    public Reservation get(Long id) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(selectByIdQuery);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ReservationMapper().mapRow(rs, rs.getRow());
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
    }

    @Override
    public Reservation get(String date, String time) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(selectByDateAndTimeQuery);
            ps.setString(1, date);
            ps.setString(2, time);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ReservationMapper().mapRow(rs, rs.getRow());
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
    }

    @Override
    public void remove(Long id) {
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(deleteByIdQuery);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
        }
    }
}