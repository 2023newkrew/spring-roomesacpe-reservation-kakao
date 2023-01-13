package nextstep.repository.theme;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.sql.*;

public class ConsoleThemeRepository implements ThemeRepository {

    private Connection con = null;

    public ConsoleThemeRepository() {
        connect();
    }

    private void connect() {
        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public Theme findByThemeId(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(findByIdSql, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return Theme.from(resultSet);
        } catch (Exception e) {
            throw new RuntimeException("테마를 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        PreparedStatement ps = con.prepareStatement(deleteByIdSql, new String[]{"id"});
        ps.setLong(1, id);
        int cnt = ps.executeUpdate();
        if (cnt == 0) throw new RuntimeException("해당 id의 테마는 존재하지 않습니다.");
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
