package nextstep.web.repository.database;

import nextstep.domain.Theme;
import nextstep.web.exception.BusinessException;
import nextstep.web.exception.CommonErrorCode;
import nextstep.web.repository.ThemeRepository;
import nextstep.web.repository.database.mappingstrategy.RowMappingStrategy;
import nextstep.web.repository.database.mappingstrategy.ThemeMappingStrategy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeDbRepository implements ThemeRepository {

    public static final String JDBC_URL = "jdbc:h2:~/test;AUTO_SERVER=true";

    public static final String JDBC_USER = "sa";

    public static final String JDBC_PASSWORD = "";

    private final RowMappingStrategy<Theme> themeRowMappingStrategy = new ThemeMappingStrategy();

    @Override
    public Long save(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            System.out.println("정상적으로 연결되었습니다.");
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new BusinessException(CommonErrorCode.SQL_CONNECTION_ERROR);
        }
    }

    @Override
    public List<Theme> findAll() {
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            List<Theme> themeList = new ArrayList<>();
            String sql = "SELECT * FROM theme;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                themeList.add(themeRowMappingStrategy.map(rs));
            }
            return themeList;
        } catch (SQLException e) {
            throw new BusinessException(CommonErrorCode.SQL_CONNECTION_ERROR);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "DELETE FROM theme WHERE ID = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new BusinessException(CommonErrorCode.SQL_CONNECTION_ERROR);
        }
    }
}
