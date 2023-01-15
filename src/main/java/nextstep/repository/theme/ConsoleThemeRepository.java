package nextstep.repository.theme;

import nextstep.ConsoleConnectDB;
import nextstep.domain.Theme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsoleThemeRepository implements ThemeRepository {

    private Connection con = null;

    public ConsoleThemeRepository() {
        con = ConsoleConnectDB.getConnect();
    }


    @Override
    public Theme findByThemeId(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(findByIdSql, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return from(resultSet);
        } catch (Exception e) {
            throw new RuntimeException("테마를 찾을 수 없습니다.");
        }
    }

    @Override
    public List<Theme> findAll() {
        try {
            PreparedStatement ps = con.prepareStatement(findAllSql, new String[]{"id"});
            ResultSet resultSet = ps.executeQuery();
            List<Theme> themeList = new ArrayList<>();
            while (resultSet.next()) {
                themeList.add(from(resultSet));
            }
            return themeList;
        } catch (Exception e) {
            throw new RuntimeException("테마를 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(deleteByIdSql, new String[]{"id"});
            ps.setLong(1, id);
            int cnt = ps.executeUpdate();
            if (cnt == 0) throw new RuntimeException("해당 id의 테마는 존재하지 않습니다.");
        } catch (Exception e) {
            throw new RuntimeException("해당 id의 테마는 존재하지 않습니다.");
        }
    }

    @Override
    public Long save(Theme theme) {
        try {
            validateReservation(theme);

            PreparedStatement ps = getReservationPreparedStatement(con, theme);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong("id");
        } catch (SQLException | IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Theme findByTheme(Theme theme) {
        try {
            PreparedStatement ps = con.prepareStatement(findByThemeSql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return from(resultSet);
        } catch (Exception e) {
            throw new RuntimeException("테마를 찾을 수 없습니다.");
        }
    }

    @Override
    public void dropThemeTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(dropTableSql);
        } catch (Exception e) {
            throw new RuntimeException("테이블 삭제 오류");
        }
    }

    @Override
    public void createThemeTable() {
        try {
            Statement statement = con.createStatement();
            statement.execute(createTableSql);
        } catch (Exception e) {
            throw new RuntimeException("테이블 생성 오류");
        }
    }

    private void validateReservation(Theme theme) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement(checkDuplicationSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, theme.getName());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int row = resultSet.getInt("total_rows");
            if (row > 0) {
                throw new IllegalArgumentException("이미 테마가 존재합니다.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("SQL 오류");
        }
    }
}
