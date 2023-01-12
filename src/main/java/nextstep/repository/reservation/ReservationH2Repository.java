package nextstep.repository.reservation;

import nextstep.domain.Reservation;
import nextstep.exception.DatabaseException;
import nextstep.exception.ReservationNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationH2Repository implements ReservationRepository {

    @Override
    public Reservation add(Reservation reservation) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Long id = rs.getLong(1);
                reservation.setId(id);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(con, ps, rs);
        }

        return reservation;
    }

    @Override
    public Reservation get(Long id) throws ReservationNotFoundException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM reservation WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return ReservationResultSetMapper.mapRow(rs);
            } else {
                throw new ReservationNotFoundException();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(con, ps, rs);
        }
    }

    @Override
    public void delete(Long id) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();
            String sql = "DELETE FROM reservation WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(con, ps ,null);
        }
    }

    @Override
    public boolean hasReservationAt(LocalDate date, LocalTime time) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            String sql = "SELECT id AS cnt FROM reservation WHERE date = ? AND time = ? LIMIT 1";
            ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(con, ps, rs);
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

    private void closeResources(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("db resource 오류:" + e.getMessage());
            throw new DatabaseException(e);
        }
    }
}
