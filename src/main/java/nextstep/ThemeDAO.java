package nextstep;

import domain.Theme;
import kakao.dto.request.UpdateThemeRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ThemeDAO {
    public final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";

    private Theme createTheme(ResultSet rs) {
        try {
            return new Theme(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4));
        } catch (SQLException e) {
            System.err.println("데이터 접근 오류 : " + e.getMessage());
        }

        return null;
    }

    public long save(Theme theme) {
        String INSERT_SQL = "insert into theme (name, desc, price) values(?, ?, ?)";

        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"id"})
        ) {
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next())
                    return -1;

                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("연결 오류 : " + e.getMessage());
        }
        return -1;
    }

    public List<Theme> themes() {
        String SELECT_SQL = "select * from theme";
        List<Theme> themes = new ArrayList<>();

        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(SELECT_SQL);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                themes.add(createTheme(rs));
            }

            return themes;
        } catch (SQLException e) {
            System.err.println("연결 오류 : " + e.getMessage());
        }

        return themes;
    }

    public Theme findById(long id) {
        String SELECT_SQL = "select * from theme where id=?";

        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(SELECT_SQL)
        ) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return createTheme(rs);
            }
        } catch (SQLException e) {
            System.err.println("연결 혹은 접근 오류" + e.getMessage());
        }

        return null;
    }

    public List<Theme> findByName(String name) {
        String SELECT_SQL = "select * from theme where name=?";
        List<Theme> result = new ArrayList<>();

        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(SELECT_SQL)
        ) {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return result;

                while (rs.next()) {
                    result.add(createTheme(rs));
                }
            }
            return result;
        } catch (SQLException e) {
            System.err.println("연결 혹은 접근 오류" + e.getMessage());
        }

        return new ArrayList<>();
    }

    public int update(UpdateThemeRequest request) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(getUpdateSQL(request))
        ) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("연결 혹은 데이터 접근 오류: " + e.getMessage());
        }
        return 0;
    }

    private String getUpdateSQL(UpdateThemeRequest request) {
        StringBuilder builder = new StringBuilder();

        builder.append("update theme set ");
        if (!Objects.isNull(request.name)) builder.append("name='").append(request.name).append("',");
        if (!Objects.isNull(request.desc)) builder.append("desc='").append(request.desc).append("',");
        if (!Objects.isNull(request.price)) builder.append("price='").append(request.price).append("',");
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" where id=").append(request.id);

        return builder.toString();
    }

    public int delete(long id) {
        String DELETE_SQL = "delete theme where id=?";

        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(DELETE_SQL)
        ) {
            ps.setLong(1, id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("연결 혹은 데이터 접근 오류 : " + e.getMessage());
        }

        return 0;
    }
}

