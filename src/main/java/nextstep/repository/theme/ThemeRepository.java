package nextstep.repository.theme;

import nextstep.domain.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public interface ThemeRepository {

    String findSql = "select * from theme where id = ?";
    String findByIdSql = "select * from theme where id = ?";
    String deleteByIdSql = "delete from theme where id = ?";

    String saveSql = "INSERT INTO theme (name, desc, price)" +
            "VALUES (?, ?, ?);";
    String checkDuplicationSql = "select count(*) as total_rows from theme where name = ?";

    String createTableSql = "";
    String dropTableSql = "";

    default PreparedStatement getReservationPreparedStatement(Connection con, Theme theme) throws SQLException {
        PreparedStatement ps = con.prepareStatement(saveSql, new String[]{"id"});
        ps.setString(1, theme.getName());
        ps.setString(2, theme.getDesc());
        ps.setInt(3, theme.getPrice());
        return ps;
    }

    Theme findByThemeId(Long id);

    void deleteById(Long id) throws SQLException;

    Long save(Theme theme);


}
