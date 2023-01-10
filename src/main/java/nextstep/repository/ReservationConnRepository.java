package nextstep.repository;

import nextstep.dto.Reservation;
import nextstep.dto.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationConnRepository implements ReservationRepository {
    private Connection conn;

    public ReservationConnRepository() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
    }

    @Override
    public Reservation create(Reservation reservation) {
        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            long id = generatedKeys.getLong("id");
            reservation.setId(id);
            return reservation;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reservation find(long id) {
        try {
            String sql = "SELECT * FROM reservation WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        try {
            String sql = "SELECT * FROM reservation";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                Theme theme = new Theme(
                        resultSet.getString("theme_name"),
                        resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price")
                );
                 reservations.add(new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime(),
                        resultSet.getString("name"),
                        theme
                ));
            }
            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            String sql = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
