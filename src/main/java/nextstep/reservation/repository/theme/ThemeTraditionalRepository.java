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
import nextstep.reservation.exceptions.exception.CustomSqlException;

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
            PreparedStatement pstmt = getAddThemePreparedStatement(theme, connection);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            return getIdFromResultSet(rs);
        }
        catch (SQLException e) {
            throw new CustomSqlException(e.getMessage());
        }
    }

    private static long getIdFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong("id");
        }
        return -1L;
    }

    private static PreparedStatement getAddThemePreparedStatement(Theme theme, Connection connection)
            throws SQLException {
        String sql = "INSERT INTO theme SET name = ?, desc = ?, price = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
        pstmt.setString(1, theme.getName());
        pstmt.setString(2, theme.getDesc());
        pstmt.setInt(3, theme.getPrice());
        return pstmt;
    }

    @Override
    public Optional<Theme> findById(Long id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement pstmt = getFindThemeByIdPreparedStatement(id, connection);
            ResultSet rs = pstmt.executeQuery();
            return getOptionalThemeFromResultSet(rs);
        } catch (SQLException e) {
            throw new CustomSqlException(e.getMessage());
        }
    }

    private static PreparedStatement getFindThemeByIdPreparedStatement(Long id, Connection connection)
            throws SQLException {
        String sql = "SELECT * FROM theme WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
        pstmt.setLong(1, id);
        return pstmt;
    }

    private static Optional<Theme> getOptionalThemeFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return Optional.of(getThemeFromResultSet(rs));
        }
        return Optional.empty();
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
    public Optional<Theme> findByName(String name) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement pstmt = getFindByNamePreparedStatement(name, connection);
            ResultSet rs = pstmt.executeQuery();
            return getOptionalThemeFromResultSet(rs);

        } catch (SQLException e) {
            throw new CustomSqlException(e.getMessage());
        }
    }

    private static PreparedStatement getFindByNamePreparedStatement(String name, Connection connection)
            throws SQLException {
        String sql = "SELECT * FROM theme WHERE name = (?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name);
        return pstmt;
    }


    @Override
    public List<Theme> findAll() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM theme";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            return getThemesFromResultSet(rs);
        } catch (SQLException e) {
            throw new CustomSqlException(e.getMessage());
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
            PreparedStatement pstmt = getDeleteThemePreparedStatement(id, connection);
            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new CustomSqlException(e.getMessage());
        }
    }

    private static PreparedStatement getDeleteThemePreparedStatement(Long id, Connection connection)
            throws SQLException {
        String sql = "DELETE FROM theme WHERE id=  ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, id);
        return pstmt;
    }
}
