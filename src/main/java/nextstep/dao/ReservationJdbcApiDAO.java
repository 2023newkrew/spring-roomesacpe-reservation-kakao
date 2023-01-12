package nextstep.dao;

import nextstep.domain.Reservation;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationJdbcApiDAO implements ReservationDAO {
    private static final String DATASOURCE_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    private static final String DATASOURCE_USER = "sa";
    private static final String DATASOURCE_PASSWORD = "";

    @Override
    public Long save(Reservation reservation) {
        Connection con = getConnection();
        Long id = -1L;

        try {
            PreparedStatementCreator insertPreparedStatementCreator = ReservationDAO.getInsertPreparedStatementCreator(reservation);
            PreparedStatement ps = insertPreparedStatementCreator.createPreparedStatement(con);

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return id;
    }

    @Override
    public Reservation findById(Long id) {
        Connection con = getConnection();
        Reservation reservation = null;

        try {
            PreparedStatement ps = con.prepareStatement(FIND_BY_ID_SQL);
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reservation = RESERVATION_ROW_MAPPER.mapRow(rs, rs.getRow());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return reservation;
    }

    @Override
    public List<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        Connection con = getConnection();
        List<Reservation> reservations = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement(FIND_BY_DATE_TIME_SQL);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reservations.add(RESERVATION_ROW_MAPPER.mapRow(rs, rs.getRow()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return reservations;
    }

    @Override
    public int deleteById(Long id) {
        Connection con = getConnection();
        int rowCount;

        try {
            PreparedStatement ps = con.prepareStatement(DELETE_BY_ID_SQL);
            ps.setLong(1, id);

            rowCount = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return rowCount;
    }

    private static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USER, DATASOURCE_PASSWORD);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    private static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }
}
