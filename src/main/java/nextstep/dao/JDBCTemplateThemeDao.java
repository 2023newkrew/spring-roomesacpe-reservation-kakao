package nextstep.dao;

import lombok.RequiredArgsConstructor;
import nextstep.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class JDBCTemplateThemeDao implements ThemeDao{

    private static final String SELECT_BY_THEME_ID_SQL = "SELECT ID, NAME, DESC, PRICE FROM THEME WHERE ID = ?";
    private static final String INSERT_SQL = "INSERT INTO `THEME`(`name`, `desc`, `price`) VALUES (?, ?, ?)";

    public final JdbcTemplate jdbcTemplate;
    @Autowired  // 생성자 하나면 @Autowired 생략 가능능
    public JDBCTemplateThemeDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private final RowMapper<Theme> themeRowMapper = (resultSet, rowNum) ->
            new Theme(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("desc"),
                    resultSet.getInt("price")
            );

    @Override
    public Theme findById(Long id) throws SQLException {
        try{
            return jdbcTemplate.queryForObject(SELECT_BY_THEME_ID_SQL, themeRowMapper, id);
        }catch(EmptyResultDataAccessException err){
            return null;
        }
    }

    @Override
    public int insert(Theme theme) throws SQLException {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        int insertCount = jdbcTemplate.update((Connection con) -> {
            PreparedStatement psmt = con.prepareStatement(INSERT_SQL, new String[] {"id"});
            int parameterIndex = 1;
            psmt.setString(parameterIndex++, theme.getName());
            psmt.setString(parameterIndex++, theme.getDesc());
            psmt.setInt(parameterIndex++, theme.getPrice());
            return psmt;
        }, keyHolder);
        theme.setId(keyHolder.getKey().longValue());

        return insertCount;
    }
}
