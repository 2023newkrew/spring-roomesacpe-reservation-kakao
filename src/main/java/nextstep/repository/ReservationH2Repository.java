package nextstep.repository;

import nextstep.Reservation;
import nextstep.exception.DatabaseException;
import nextstep.exception.ReservationNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationH2Repository implements ReservationRepository {

    @Override
    public Reservation add(Reservation reservation) {
        Connection con = getConnection();

        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Long id = rs.getLong(1);
                reservation.setId(id);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeConnection(con);
        }

        return reservation;
    }

    @Override
    public Reservation get(Long id) throws ReservationNotFoundException {
        Connection con = getConnection();
        Reservation result = null;

        try {
            String sql = "SELECT * FROM reservation WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = ReservationResultSetMapper.mapRow(rs);
            } else {
                throw new ReservationNotFoundException();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeConnection(con);
        }

        return result;
    }

    @Override
    public void delete(Long id) {
        Connection con = getConnection();

        try {
            String sql = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public boolean hasReservationAt(LocalDate date, LocalTime time) {
        Connection con = getConnection();

        try {
            String sql = "SELECT count(*) AS cnt FROM reservation WHERE date = ? AND time = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ResultSet rs = ps.executeQuery();

            rs.next();
            int cnt = rs.getInt("cnt");
            return cnt >= 1;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeConnection(con);
        }
    }

    private Connection getConnection() {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException(e);
        }
        return con;
    }

    private void closeConnection(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
            throw new DatabaseException(e);
        }
    }
}
