package nextstep.theme.dao;

import lombok.RequiredArgsConstructor;
import nextstep.theme.entity.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCTemplateThemeDao implements ThemeDao {

    private static final String SELECT_BY_THEME_ID_SQL = "SELECT `ID`, `NAME`, `DESC`, `PRICE` FROM THEME WHERE `ID` = ?";
    private static final String INSERT_SQL = "INSERT INTO `THEME`(`NAME`, `DESC`, `PRICE`) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Theme> themeRowMapper = (resultSet, rowNum) ->
            new Theme(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("desc"),
                    resultSet.getInt("price")
            );

    @Override
    public Optional<Theme> findById(Long id){
        return jdbcTemplate.query(SELECT_BY_THEME_ID_SQL, themeRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public int insert(Theme theme){
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