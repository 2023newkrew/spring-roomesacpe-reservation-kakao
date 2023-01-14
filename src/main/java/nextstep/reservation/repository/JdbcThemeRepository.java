package nextstep.reservation.repository;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exception.RoomEscapeException;
import nextstep.reservation.exception.RoomEscapeExceptionCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcThemeRepository implements ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Theme save(Theme theme) {
        String sql = "insert into THEME (name, desc, price) values (?, ?, ?)";
        KeyHolder themeKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((con -> {
            PreparedStatement psmt = con.prepareStatement(sql, new String[]{"id"});
            psmt.setString(1, theme.getName());
            psmt.setString(2, theme.getDesc());
            psmt.setInt(3, theme.getPrice());

            return psmt;
        }), themeKeyHolder);

        return Theme.builder()
                .id(themeKeyHolder.getKey().longValue())
                .name(theme.getName())
                .desc(theme.getDesc())
                .price(theme.getPrice())
                .build();
    }

    @Override
    public Optional<Theme> findById(long id) {
        String sql = "select * from THEME where id = ?";
        List<Theme> theme = jdbcTemplate.query(sql, themeRowMapper(), id);
        return theme.stream().findAny();
    }

    @Override
    public List<Theme> findAll() {
        String sql = "select * from THEME";
        List<Theme> themeList = jdbcTemplate.query(sql, themeRowMapper());
        return themeList;
    }

    @Override
    public int deleteById(long id) {
        String existsQuery = "select exists(select * from RESERVATION where theme_id = id)";
        Boolean isExistInReservation = jdbcTemplate.queryForObject(existsQuery, Boolean.class, id);
        if (isExistInReservation) {
            throw new RoomEscapeException(RoomEscapeExceptionCode.RESERVATION_EXIST);
        }
        String sql = "delete from THEME where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public void clear() {
        String sql = "delete from THEME";
        jdbcTemplate.update(sql);
    }

    private RowMapper<Theme> themeRowMapper() {
        return (rs, rowNum) -> Theme.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .desc(rs.getString("desc"))
                .price(rs.getInt("price"))
                .build();
    }
}
