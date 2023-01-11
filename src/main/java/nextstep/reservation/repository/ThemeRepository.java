package nextstep.reservation.repository;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exceptions.exception.DuplicateReservationException;
import nextstep.reservation.exceptions.exception.DuplicateThemeException;
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
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(con -> {
                PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
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
        String sql = "SELECT * FROM theme";
        RowMapper<Theme> rowMapper = (rs, rowNum) -> Theme.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .desc(rs.getString("desc"))
                .price(rs.getInt("price"))
                .build();

        List<Theme> query = jdbcTemplate.query(sql, rowMapper);
        query.forEach(System.out::println);

        return query;
    }

    public void delete(final Long id) {
        String sql = "DELETE FROM theme WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
