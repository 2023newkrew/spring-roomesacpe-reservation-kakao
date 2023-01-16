package nextstep.repository.theme;

import nextstep.domain.Theme;
import nextstep.exception.ReservationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static nextstep.exception.ErrorCode.*;

public class ConsoleThemeRepository extends ThemeRepository {

    //TODO: 코드 중복 제거(Connection 주입?)
    private static final String CONNECTION_SUCCESS_MESSAGE = "정상적으로 연결되었습니다.";
    private static final String DATABASE_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    private static final String DATABASE_USER_ID = "sa";
    private static final String DATABASE_USER_PASSWORD = "";

    private Connection con = null;

    public ConsoleThemeRepository() {
        connect();
    }

    private void connect() {
        // 드라이버 연결
        try {
            con = DriverManager.getConnection(DATABASE_URL, DATABASE_USER_ID, DATABASE_USER_PASSWORD);
            System.out.println(CONNECTION_SUCCESS_MESSAGE);
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
        }
    }

    @Override
    public Long save(String name, String desc, Integer price) {
        try {
            validateTheme(name);

            PreparedStatement ps = getThemePreparedStatement(con, name, desc, price);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong("id");
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
        }
    }

    private void validateTheme(String name) {
        try {
            PreparedStatement ps = con.prepareStatement(CHECK_DUPLICATION_SQL);
            ps.setString(1, name);

            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            int row = resultSet.getInt("total_rows");
            if (row > 0) {
                throw new ReservationException(DUPLICATED_THEME_EXISTS);
            }
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
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
                themes.add(Theme.from(resultSet));
            }

            if (themes.size() == 0) {
                throw new ReservationException(THEME_NOT_FOUND);
            }

            return themes;
        } catch (SQLException e) {
            throw new ReservationException(THEME_NOT_FOUND);
        }
    }

    @Override
    public Theme findById(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(FIND_BY_ID_SQL, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return Theme.from(resultSet);
        } catch (SQLException e) {
            throw new ReservationException(THEME_NOT_FOUND);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(DELETE_BY_ID_SQL, new String[]{"id"});
            ps.setLong(1, id);
            int updatedRows = ps.executeUpdate();
            if (updatedRows == 0) {
                throw new ReservationException(THEME_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
        }
    }

    @Override
    public void createTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
        }
    }

    @Override
    public void dropTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(DROP_TABLE_SQL);
        } catch (SQLException e) {
            throw new ReservationException(SQL_ERROR);
        }
    }
}
