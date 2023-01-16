package roomescape.theme.repository.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import roomescape.entity.Theme;

public class ThemeDaoWithoutTemplateImpl implements ThemeDao {
    @Override
    public Long create(Theme theme) {
        String sql = "insert into theme (name, desc, price) values (?, ?, ?)";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, theme.getName());
            pstmt.setString(2, theme.getDesc());
            pstmt.setString(3, String.valueOf(theme.getPrice()));
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Theme> selectById(Long id) {
        String sql = "select * from theme WHERE id = ?";
        Theme theme = null;
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                theme = Theme.builder()
                        .themeId(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .desc(rs.getString("desc"))
                        .price(rs.getInt("price"))
                        .build();
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(theme);
    }

    @Override
    public List<Theme> selectAll() {
        String sql = "select * from theme";
        List<Theme> themes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                themes.add(Theme.builder()
                        .themeId(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .desc(rs.getString("desc"))
                        .price(rs.getInt("price"))
                        .build());
            }
        } catch (SQLException e) {
        }
        return themes;
    }

    @Override
    public int delete(Long id) {
        String sql = "delete from theme where id = ?";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isThemeNameDuplicated(String themeName) {
        String sql = "select count(*) from theme where name =  ?";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/text", "sa", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, themeName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }
}
