package nextstep.theme.dao;

import lombok.RequiredArgsConstructor;
import nextstep.theme.entity.Theme;
import nextstep.util.DatabaseUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DirectConnectionThemeDao implements ThemeDao {
    private static final String SELECT_BY_ID_SQL = "SELECT ID, NAME, DESC, PRICE FROM THEME WHERE ID = ?";
    private static final String SELECT_ALL_SQL = "SELECT `ID`, `NAME`, `DESC`, `PRICE` FROM `THEME`";
    private static final String SELECT_BY_NAME_SQL = "SELECT `ID`, `NAME`, `DESC`, `PRICE` FROM `THEME` WHERE `NAME` = ?";
    private static final String UPDATE_SQL = "UPDATE `THEME` SET `NAME` = ?, `DESC` = ?, `PRICE` = ? WHERE `ID` = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM `THEME` WHERE `ID` = ?";
    private static final String INSERT_SQL = "INSERT INTO `THEME`(`name`, `desc`, `price`) VALUES (?, ?, ?)";


    private final DataSource dataSource;

    @Override
    public Optional<Theme> findById(Long id){
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet resultSet = null;

        try{
            con = dataSource.getConnection();
            psmt = con.prepareStatement(SELECT_BY_ID_SQL);
            setTheme(id, psmt);

            resultSet = psmt.executeQuery();
            List<Theme> themes = getTheme(resultSet);
            return themes.stream().findFirst();
        }catch (SQLException sqlException){
            return Optional.empty();
        }finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }

    @Override
    public List<Theme> findAll() {
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet resultSet = null;

        try{
            con = dataSource.getConnection();
            psmt = con.prepareStatement(SELECT_ALL_SQL);
            resultSet = psmt.executeQuery();
            return getTheme(resultSet);
        }catch (SQLException sqlException){
            return List.of();
        }finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }

    @Override
    public Optional<Theme> findByName(String name) {
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet resultSet = null;

        try{
            con = dataSource.getConnection();
            psmt = con.prepareStatement(SELECT_BY_NAME_SQL);
            int parameterIndex = 1;
            psmt.setString(parameterIndex++, name);

            resultSet = psmt.executeQuery();
            List<Theme> themes = getTheme(resultSet);
            return themes.stream().findFirst();
        }catch (SQLException sqlException){
            return Optional.empty();
        }finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }

    @Override
    public int insert(Theme theme){
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        Long themeId = 0L;
        int insertCount = 0;

        try{
            con = dataSource.getConnection();
            psmt = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);

            setTheme(theme, psmt);

            insertCount = psmt.executeUpdate();
            resultSet = psmt.getGeneratedKeys();

            theme.setId(getThemeId(resultSet));
        }catch (SQLException sqlException){

        }finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
        return insertCount;
    }

    @Override
    public int update(Theme theme) {
        Connection con = null;
        PreparedStatement psmt = null;
        int updateCount = 0;

        try{
            con = dataSource.getConnection();
            psmt = con.prepareStatement(UPDATE_SQL);

            setTheme(theme, psmt);
            updateCount = psmt.executeUpdate();
        }catch (SQLException sqlException){

        }finally {
            DatabaseUtil.close(con, psmt);
        }
        return updateCount;
    }

    @Override
    public int deleteById(Long id) {
        Connection con = null;
        PreparedStatement psmt = null;
        int deleteCount = 0;

        try{
            con = dataSource.getConnection();
            psmt = con.prepareStatement(DELETE_BY_ID_SQL);
            psmt.setLong(1, id);
            deleteCount = psmt.executeUpdate();
        }catch (SQLException sqlException){

        }finally {
            DatabaseUtil.close(con, psmt);
        }
        return deleteCount;
    }

    private static void setTheme(Long id, PreparedStatement psmt) throws SQLException {
        psmt.setLong(1, id);
    }

    private static void setTheme(Theme theme, PreparedStatement psmt) throws SQLException {
        int parameterIndex = 1;
        psmt.setString(parameterIndex++, theme.getName());
        psmt.setString(parameterIndex++, theme.getDesc());
        psmt.setInt(parameterIndex++, theme.getPrice());
    }

    private static Long getThemeId(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getLong(1);
        }
        return null;
    }

    private static List<Theme> getTheme(ResultSet resultSet) throws SQLException {
        List<Theme> themes = new ArrayList<>();
        while(resultSet.next()){
            Long id  = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");
            String desc = resultSet.getString("DESC");
            Integer price = resultSet.getInt("PRICE");
            themes.add((new Theme(id, name, desc, price)));
        }
        return themes;
    }
}
