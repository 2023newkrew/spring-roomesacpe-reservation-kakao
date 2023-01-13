package roomescape.repository;

import roomescape.domain.Theme;
import roomescape.repository.mapper.ThemeMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThemeConsoleRepository implements ThemeRepository {

    private final String DB_URL = "jdbc:h2:mem:testdb;AUTO_SERVER=true";
    private final String DB_USERNAME = "sa";
    private final String DB_PASSWORD = "";

    @Override
    public List<Theme> findAllThemes() {
        List<Theme> themes = new ArrayList<>();
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createFindAllThemesPreparedStatement(con);
                ResultSet resultSet = ps.executeQuery()
        ) {
            while (resultSet.next()) {
                Theme theme = ThemeMapper.mapToTheme(resultSet);
                themes.add(theme);
            }
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
            e.printStackTrace();
        }
        return themes;
    }

    private PreparedStatement createFindAllThemesPreparedStatement(Connection con) throws SQLException {
        String sql = "SELECT * FROM theme";
        return con.prepareStatement(sql);
    }

    @Override
    public Optional<Theme> findThemeById(Long id) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createfindThemeByIdPreparedStatement(con, id);
                ResultSet resultSet = ps.executeQuery()
        ) {
            return resultSetToTheme(resultSet);
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private PreparedStatement createfindThemeByIdPreparedStatement(Connection con, Long id) throws SQLException {
        String sql = "SELECT * FROM theme WHERE id = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);

        return ps;
    }

    private Optional<Theme> resultSetToTheme(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(ThemeMapper.mapToTheme(resultSet));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Theme> findThemeByName(String name) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createfindThemeByNamePreparedStatement(con, name);
                ResultSet resultSet = ps.executeQuery()
        ) {
            return resultSetToTheme(resultSet);
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private PreparedStatement createfindThemeByNamePreparedStatement(Connection con, String name) throws SQLException {
        String sql = "SELECT * FROM theme WHERE name = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);

        return ps;
    }

    @Override
    public Long insertTheme(Theme theme) {
        ResultSet resultSet = null;
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createInsertThemePreparedStatement(con, theme)
        ) {
            ps.executeUpdate();
            resultSet = ps.getGeneratedKeys();
            return resultSetToThemeId(resultSet);
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return -1L;
    }

    private PreparedStatement createInsertThemePreparedStatement(Connection con, Theme theme) throws SQLException {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
        ps.setString(1, theme.getName());
        ps.setString(2, theme.getDesc());
        ps.setInt(3, theme.getPrice());

        return ps;
    }

    private Long resultSetToThemeId(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getLong("id");
        }
        return -1L;
    }

    @Override
    public void changeTheme(Long id, String name, String desc, int price) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createChangeThemePreparedStatement(con, id, name, desc, price)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private PreparedStatement createChangeThemePreparedStatement(Connection con, Long id, String name, String desc, int price) throws SQLException {
        String sql = "UPDATE theme SET name = (?), desc = (?), price = (?) where id = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, desc);
        ps.setInt(3, price);
        ps.setLong(4, id);

        return ps;
    }

    @Override
    public void deleteTheme(Long id) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createDeleteThemePreparedStatement(con, id)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private PreparedStatement createDeleteThemePreparedStatement(Connection con, Long id) throws SQLException {
        String sql = "DELETE FROM theme WHERE id = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);

        return ps;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM theme";
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
        }
    }
}
