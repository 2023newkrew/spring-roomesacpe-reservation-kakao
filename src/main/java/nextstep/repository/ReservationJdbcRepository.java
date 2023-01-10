package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationJdbcRepository implements ReservationRepository{

    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/Projects/h2/db/roomescape;AUTO_SERVER=true";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, new String[]{"id"});

            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());

            ps.executeUpdate();

            resultSet = ps.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new SQLException();
            }

            reservation.setId(resultSet.getLong("id"));
            return reservation;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, ps, resultSet);
        }
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);

            ps.setLong(1, id);

            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractReservation(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, ps, resultSet);
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);

            resultSet = ps.executeQuery();

            return extractReservations(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, ps, resultSet);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            throw new IllegalStateException(e);
        } finally {
            close(conn, ps, null);
        }
    }

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void close(Connection conn, PreparedStatement ps, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private Reservation extractReservation(ResultSet resultSet) throws SQLException {
        Theme theme = new Theme(
                resultSet.getString("theme_name"),
                resultSet.getString("theme_desc"),
                resultSet.getInt("theme_price")
        );
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                theme
        );
    }

    private List<Reservation> extractReservations(ResultSet resultSet) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        while (resultSet.next()) {
            reservations.add(extractReservation(resultSet));
        }
        return reservations;
    }
}
