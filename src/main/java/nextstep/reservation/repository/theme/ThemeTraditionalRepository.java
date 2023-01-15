package nextstep.reservation.repository.theme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nextstep.reservation.entity.Theme;

public class ThemeTraditionalRepository implements ThemeRepository{

    private final String url;
    private final String user;
    private final String password;

    public ThemeTraditionalRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Long add(Theme theme) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO theme SET name = ?, desc = ?, price = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, theme.getName());
            pstmt.setString(2, theme.getDesc());
            pstmt.setInt(3, theme.getPrice());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            return rs.next() ? rs.getLong("id") : -1L;
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Theme> findById(Long id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM theme WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? Optional.of(getThemeFromResultSet(rs)) : Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Theme getThemeFromResultSet(ResultSet rs) throws SQLException {
        return Theme.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .desc(rs.getString("desc"))
                .price(rs.getInt("price"))
                .build();
    }

    @Override
    public List<Theme> findAll() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM theme";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            return getThemesFromResultSet(rs);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private static List<Theme> getThemesFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<Theme> themes = new ArrayList<>();
        while (rs.next()) {
            themes.add(getThemeFromResultSet(rs));
        }
        return themes;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM theme WHERE id=  ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
