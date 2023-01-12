package nextstep.reservations.domain.repository.theme;

import lombok.RequiredArgsConstructor;
import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.exceptions.theme.exception.DuplicateThemeException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public Long add(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(con -> {
                PreparedStatement pstmt = con.prepareStatement(ThemeQuery.INSERT_ONE.get(), new String[]{"id"});
                pstmt.setString(1, theme.getName());
                pstmt.setString(2, theme.getDesc());
                pstmt.setLong(3, theme.getPrice());
                return pstmt;
            }, keyHolder);
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateThemeException();
        }

        return keyHolder.getKey().longValue();
    }

    public List<Theme> findAll() {
        RowMapper<Theme> rowMapper = (rs, rowNum) -> Theme.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .desc(rs.getString("desc"))
                .price(rs.getInt("price"))
                .build();

        return jdbcTemplate.query(ThemeQuery.FIND_ALL.get(), rowMapper);
    }

    public void delete(final Long id) {
        jdbcTemplate.update(ThemeQuery.REMOVE_BY_ID.get(), id);
    }
}
