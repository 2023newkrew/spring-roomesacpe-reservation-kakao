package nextstep.repository;

import nextstep.ConsoleConnectDB;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.Statement;

public class ResetTable {

    private Connection con = ConsoleConnectDB.getConnect();

    private final JdbcTemplate jdbcTemplate;

    final String createThemeSql = "CREATE TABLE theme" +
            "(" +
            "    id    bigint not null auto_increment," +
            "    name  varchar(20)," +
            "    desc  varchar(255)," +
            "    price int," +
            "    primary key (id)" +
            ");";
    final String dropThemeSql = "drop table if exists theme";

    final String createReservationSql = "create table reservation (" +
            "  id bigint not null auto_increment," +
            "  date date," +
            "  time time," +
            "  name varchar(20)," +
            "  theme_id bigint," +
            "  primary key (id)" +
            ");";
    final String dropReservationSql = "drop table if exists reservation";

    public ResetTable(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void consoleThemeTableReset() {
        dropConsoleThemeTable();
        createConsoleThemeTable();
    }

    private void createConsoleThemeTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(createThemeSql);
        } catch (Exception e) {
            throw new RuntimeException("테이블 생성 오류");
        }
    }

    private void dropConsoleThemeTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(dropThemeSql);
        } catch (Exception e) {
            throw new RuntimeException("테이블 삭제 오류");
        }
    }

    public void consoleReservationReset() {
        dropConsoleReservationTable();
        createConsoleReservationTable();
    }

    private void createConsoleReservationTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(createReservationSql);
        } catch (Exception e) {
            throw new RuntimeException("테이블 생성 오류");
        }
    }

    private void dropConsoleReservationTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(dropReservationSql);
        } catch (Exception e) {
            throw new RuntimeException("테이블 삭제 오류");
        }
    }

    public void jdbcThemeReset() {
        jdbcTemplate.execute(dropThemeSql);
        jdbcTemplate.execute(createThemeSql);
    }

    public void jdbcReservationReset() {
        jdbcTemplate.execute(dropReservationSql);
        jdbcTemplate.execute(createReservationSql);
    }
}
