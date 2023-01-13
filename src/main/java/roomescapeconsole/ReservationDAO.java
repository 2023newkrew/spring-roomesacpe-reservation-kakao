package roomescapeconsole;

import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.rmi.NoSuchObjectException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDAO {

    private static final int INDEX_COUNT = 1;
    private static final int INDEX_ID = 1;
    private static final int INDEX_DATE = 2;
    private static final int INDEX_TIME = 3;
    private static final int INDEX_NAME = 4;
    private static final int INDEX_THEME_NAME = 5;
    private static final int INDEX_THEME_DESC = 6;
    private static final int INDEX_THEME_PRICE = 7;
    private static final String DATABASE_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    private static final String DATABASE_USERNAME = "sa";
    private static final String DATABASE_PASSWORD = "";
    private static final String CHECK_NUMBER_QUERY = "SELECT COUNT(*) FROM RESERVATION";
    private static final String INSERT_QUERY = "INSERT INTO RESERVATION (id, date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_QUERY = "SELECT * FROM RESERVATION WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM RESERVATION WHERE id=?";

    public Connection makeConnection() {
        Connection con;
        try {
            con = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            System.out.println("정상적으로 연결되었습니다.");
            return con;
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getNumberOfExistReservation() {
        try (Connection con = makeConnection()) {
            PreparedStatement getNumberPS = con.prepareStatement(CHECK_NUMBER_QUERY);
            ResultSet getNumberRs = getNumberPS.executeQuery();
            if (getNumberRs.next()) {
                return getNumberRs.getInt(INDEX_COUNT);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void addReservation(Reservation reservation) {
        try (Connection con = makeConnection()) {
            PreparedStatement addPs = con.prepareStatement(INSERT_QUERY);
            addPs.setLong(INDEX_ID, reservation.getId());
            addPs.setDate(INDEX_DATE, Date.valueOf(reservation.getDate()));
            addPs.setTime(INDEX_TIME, Time.valueOf(reservation.getTime()));
            addPs.setString(INDEX_NAME, reservation.getName());
            addPs.setString(INDEX_THEME_NAME, reservation.getTheme().getName());
            addPs.setString(INDEX_THEME_DESC, reservation.getTheme().getDesc());
            addPs.setInt(INDEX_THEME_PRICE, reservation.getTheme().getPrice());
            addPs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation lookUpReservation(Long findId) throws NoSuchObjectException {
        try (Connection con = makeConnection()) {
            PreparedStatement lookUpPS = con.prepareStatement(SELECT_QUERY);
            lookUpPS.setString(INDEX_ID, String.valueOf(findId));
            ResultSet lookUpRs = lookUpPS.executeQuery();
            if (lookUpRs.next()) {
                Long id = lookUpRs.getLong(INDEX_ID);
                LocalDate date = LocalDate.parse(lookUpRs.getString(INDEX_DATE));
                LocalTime time = LocalTime.parse(lookUpRs.getString(INDEX_TIME));
                String name = lookUpRs.getString(INDEX_NAME);
                String themeName = lookUpRs.getString(INDEX_THEME_NAME);
                String themeDesc = lookUpRs.getString(INDEX_THEME_DESC);
                Integer themePrice = lookUpRs.getInt(INDEX_THEME_PRICE);
                Theme theme = new Theme(themeName, themeDesc, themePrice);
                return new Reservation(id, date, time, name, theme);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new NoSuchObjectException("No Object");
    }

    public void deleteReservation(Long deleteId) {
        try (Connection con = makeConnection()) {
            PreparedStatement deletePs = con.prepareStatement(DELETE_QUERY);
            deletePs.setString(INDEX_ID, String.valueOf(deleteId));
            deletePs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}