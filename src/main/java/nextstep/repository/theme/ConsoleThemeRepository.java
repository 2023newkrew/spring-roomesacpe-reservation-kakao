package nextstep.repository.theme;

import nextstep.config.DbConfig;
import nextstep.domain.Theme;
import nextstep.exception.EscapeException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static nextstep.exception.ErrorCode.*;

public class ConsoleThemeRepository extends ThemeRepository {

    private Connection con = null;
    private DbConfig dbConfig = new DbConfig();

    public ConsoleThemeRepository() {
        connect();
    }

    private void connect() {
        // 드라이버 연결
        try {
            con = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }

    @Override
    public Long save(String name, String desc, Integer price) {
        try {
            validateTheme(name);

            PreparedStatement ps = getThemePreparedStatement(con, name, desc, price);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong("id");
            }
            throw new EscapeException(THEME_CREATE_FAILED);
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }

    private void validateTheme(String name) {
        try {
            PreparedStatement ps = con.prepareStatement(CHECK_DUPLICATION_SQL);
            ps.setString(1, name);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                // 동일한 테마가 이미 존재한다면 예외 던짐
                int row = resultSet.getInt("total_rows");
                if (row > 0) {
                    throw new EscapeException(DUPLICATED_THEME_EXISTS);
                }
            }
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }

    @Override
    public Long save(Theme theme) {
        return this.save(theme.getName(), theme.getDesc(), theme.getPrice());
    }

    @Override
    public List<Theme> findAll() {
        try {
            PreparedStatement ps = con.prepareStatement(FIND_ALL_SQL, new String[]{"id"});
            ResultSet resultSet = ps.executeQuery();

            List<Theme> themes = new ArrayList<>();

            while (resultSet.next()) {
                themes.add(extractTheme(resultSet));
            }

            if (themes.size() == 0) {
                throw new EscapeException(THEME_NOT_FOUND);
            }

            return themes;
        } catch (SQLException e) {
            throw new EscapeException(THEME_NOT_FOUND);
        }
    }

    @Override
    public Theme findById(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(FIND_BY_ID_SQL, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            // 해당하는 데이터가 있으면 파싱 후 리턴, 없으면 예외 던짐
            if (resultSet.next()) {
                return extractTheme(resultSet);
            }
            throw new EscapeException(THEME_NOT_FOUND);
        } catch (SQLException e) {
            throw new EscapeException(THEME_NOT_FOUND);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(DELETE_BY_ID_SQL, new String[]{"id"});
            ps.setLong(1, id);
            int updatedRows = ps.executeUpdate();
            if (updatedRows == 0) {
                throw new EscapeException(THEME_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }

    @Override
    public void createTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }

    @Override
    public void dropTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(DROP_TABLE_SQL);
        } catch (SQLException e) {
            throw new EscapeException(SQL_ERROR);
        }
    }
}
