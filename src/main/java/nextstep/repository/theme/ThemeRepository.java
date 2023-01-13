package nextstep.repository.theme;

import nextstep.domain.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ThemeRepository {

    String findSql = "select * from theme where id = ?";
    String findByIdSql = "select * from theme where id = ?";
    String findByThemeSql = "select * from theme where name = ? and desc = ? and price = ?";
    String findAllSql = "select * from theme";
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

    default Theme from(ResultSet resultSet) throws SQLException {
        return new Theme(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price")
        );
    }

    Theme findByThemeId(Long id);
    List<Theme> findAll();
    void deleteById(Long id) throws SQLException;

    Long save(Theme theme);

    Theme findByTheme(Theme theme);
}
