package nextstep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Initiator {
    private final String DB_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";

    public void initReservation() {
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement truncatePs = con.prepareStatement("truncate table reservation");
                PreparedStatement restartPs = con.prepareStatement("ALTER TABLE reservation ALTER COLUMN ID RESTART WITH 1")
        ) {
            truncatePs.executeUpdate();
            restartPs.execute();
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void initTheme() {
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement setReferentialIntegrityPs = con.prepareStatement("SET REFERENTIAL_INTEGRITY FALSE");
                PreparedStatement truncatePs = con.prepareStatement("TRUNCATE TABLE theme");
                PreparedStatement restartPs = con.prepareStatement("ALTER TABLE theme ALTER COLUMN id RESTART WITH 1");
                PreparedStatement setOffReferentialIntegrityPs = con.prepareStatement("SET REFERENTIAL_INTEGRITY TRUE")
        ) {
            setReferentialIntegrityPs.execute();
            truncatePs.executeUpdate();
            restartPs.execute();
            setOffReferentialIntegrityPs.execute();
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createSingleTheme() {
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement insertThemePs = con.prepareStatement("INSERT INTO theme (name, desc, price) VALUES ('themeName','themeDesc',4000)")
        ) {
            insertThemePs.executeUpdate();
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
        }
    }
}
