package nextstep.repository.theme;

import nextstep.domain.Theme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class ThemeRepository {

    protected String FIND_BY_ID_SQL = "select * from theme where id = ?";

    protected String FIND_ALL_SQL = "select * from theme";
    protected String DELETE_BY_ID_SQL = "delete from theme where id = ?";
    protected String SAVE_SQL = "INSERT INTO theme (name, desc, price)" +
            "VALUES (?, ?, ?);";
    protected String CHECK_DUPLICATION_SQL = "select count(*) as total_rows from theme where name = ?";
    protected String CREATE_TABLE_SQL = "create table theme (\n" +
            "  id bigint not null auto_increment,\n" +
            "  name varchar(20),\n" +
            "  desc varchar(255),\n" +
            "  price int,\n" +
            "  primary key (id)\n" +
            ");";
    protected String DROP_TABLE_SQL = "drop table theme if exists cascade";

    protected PreparedStatement getThemePreparedStatement(Connection con, String name, String desc, Integer price)
            throws SQLException {
        PreparedStatement ps = con.prepareStatement(SAVE_SQL, new String[]{"id"});
        ps.setString(1, name);
        ps.setString(2, desc);
        ps.setInt(3, price);
        return ps;
    }

    public abstract Long save(String name, String desc, Integer price);

    public abstract Long save(Theme theme);

    public abstract List<Theme> findAll();

    public abstract Theme findById(Long id);

    public abstract void deleteById(Long id);

    public abstract void createTable();

    public abstract void dropTable();

    public Theme extractTheme(ResultSet resultSet) throws SQLException {
        return new Theme(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price"));
    }
}