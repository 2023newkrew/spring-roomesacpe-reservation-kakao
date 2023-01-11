package nextstep.dao;

import nextstep.entity.Theme;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DirectConnectionThemeDao implements ThemeDao {
    private static final String FIND_BY_ID_SQL = "SELECT ID, NAME, DESC, PRICE FROM THEME WHERE ID = ?";
    private static final String INSERT_SQL = "INSERT INTO `THEME`(`name`, `desc`, `price`) VALUES (?, ?, ?)";

    private final DataSource dataSource;

    @Override
    public Theme findById(Long id) throws SQLException{
        Connection con = dataSource.getConnection();
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        try{
            psmt = con.prepareStatement(FIND_BY_ID_SQL);
            psmt.setLong(1, id);
            resultSet = psmt.executeQuery();
            List<Theme> themes = getTheme(id, resultSet);
            return (themes.size() > 0) ? themes.get(0) : null;
        }catch (SQLException sqlException){
            return null;
        }finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }

    @Override
    public int insert(Theme theme) throws SQLException{
        Connection con = dataSource.getConnection();
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        Long themeId = 0L;
        int insertCount = 0;
        try{
            int parameterIndex = 1;
            psmt = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            psmt.setString(parameterIndex++, theme.getName());
            psmt.setString(parameterIndex++, theme.getDesc());
            psmt.setInt(parameterIndex++, theme.getPrice());

            insertCount = psmt.executeUpdate();
            resultSet = psmt.getGeneratedKeys();

            while (resultSet.next()) {
                themeId = resultSet.getLong(1);
            }
            theme.setId(themeId);
        }catch (SQLException sqlException){
            throw sqlException;
        }finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
        return themeId;
    }

    private static List<Theme> getTheme(Long id, ResultSet resultSet) throws SQLException {
        List<Theme> themes = new ArrayList<>();
        while(resultSet.next()){
            String name = resultSet.getString("NAME");
            String desc = resultSet.getString("DESC");
            Integer price = resultSet.getInt("PRICE");
            themes.add((new Theme(id, name, desc, price)));
        }
        return themes;
    }
}
