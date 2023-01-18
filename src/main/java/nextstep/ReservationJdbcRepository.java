package nextstep;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import reservation.model.domain.Reservation;
import reservation.model.domain.Theme;
import reservation.respository.ReservationRepository;
import reservation.util.exception.db.ConnectionException;
import reservation.util.exception.db.QueryException;

import java.sql.*;

import static reservation.util.exception.ErrorMessages.CONNECTION_ERROR;
import static reservation.util.exception.ErrorMessages.QUERY_ERROR;

public class ReservationJdbcRepository implements ReservationRepository {

    private final String DB_URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE";
    private final String DB_USER = "sa";
    private final String DB_PW = "";

    private Connection makeConnection(){
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
            assert con != null;
            System.out.println("정상적으로 연결되었습니다.");
            return con;
        } catch (SQLException | AssertionError e) {
            throw new ConnectionException(CONNECTION_ERROR);
        }
    }

    @Override
    public Long save(Reservation reservation) {
        Long id = 0L;
        try (Connection con = makeConnection()){
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            assert con != null;
            KeyHolder keyHolder = new GeneratedKeyHolder();
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getThemeId());
            ps.executeUpdate();
            id = (long) keyHolder.getKey();
            ps.close();
        } catch (SQLException | AssertionError e) {
            throw new QueryException(QUERY_ERROR);
        }
        return id;
    }

    @Override
    public Reservation findById(Long id) {
        Reservation reservation = null;
        try (Connection con = makeConnection()){
            String sql = "SELECT date, time, name, theme_name, theme_desc, theme_price FROM reservation WHERE id = ?;";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reservation = new Reservation(
                        id,
                        rs.getDate(1).toLocalDate(),
                        rs.getTime(2).toLocalTime(),
                        rs.getString(3),
                        rs.getLong(4)
                );
            }

            rs.close();
            ps.close();
        } catch (SQLException | AssertionError e) {
            throw new QueryException(QUERY_ERROR);
        }

        if (reservation == null) {
            throw new RuntimeException();
        }

        return reservation;
    }

    @Override
    public int deleteById(Long id) {
        int row;
        try (Connection con = makeConnection()){
            String sql = "DELETE FROM reservation WHERE id = ?;";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            row = ps.executeUpdate();
            ps.close();
        } catch (SQLException | AssertionError e) {
            throw new QueryException(QUERY_ERROR);
        }
        return row;
    }
}