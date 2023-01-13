package roomescape.repository;

import roomescape.model.Theme;
import roomescape.repository.ThemeRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThemeConsoleRepository implements ThemeRepository {
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    private static final String DB_USER_NAME = "sa";
    private static final String DB_PASSWORD = "";

    @Override
    public long save(Theme theme) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return (long) ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Theme> findOneById(Long themeId) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "select * from theme where id = ? limit 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, themeId);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return Optional.of(new Theme(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("desc"),
                    resultSet.getInt("price"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Theme> findOneByName(String themeName) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "select * from theme where name = ? limit 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, themeName);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return Optional.of(new Theme(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("desc"),
                    resultSet.getInt("price"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Theme> findAll() {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "select * from theme";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Theme> themes = new ArrayList<>();
            while (resultSet.next()) {
                themes.add(
                        new Theme(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("desc"),
                                resultSet.getInt("price")
                        )
                );
            }
            return themes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long themeId) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "DELETE FROM theme WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, themeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean hasThemeWithName(String themeName) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "select * from theme where name = ? limit 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, themeName);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateNameOfId(Long themeId, String newName) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "update theme set name = ? where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setLong(2, themeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDescOfId(Long themeId, String newDesc) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "update theme set desc = ? where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newDesc);
            ps.setLong(2, themeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePriceOfId(Long themeId, Integer newPrice) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "update theme set price = ? where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, newPrice);
            ps.setLong(2, themeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
