package nextstep.repository;

import static nextstep.domain.ThemeConstants.THEME_DESC;
import static nextstep.domain.ThemeConstants.THEME_NAME;
import static nextstep.domain.ThemeConstants.THEME_PRICE;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import nextstep.domain.Theme;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public ReservationRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public Long save(ReservationRequestDTO reservationRequestDTO) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date", Date.valueOf(reservationRequestDTO.getDate()));
        parameters.put("time", Time.valueOf(reservationRequestDTO.getTime()));
        parameters.put("name", reservationRequestDTO.getName());
        parameters.put("theme_name", THEME_NAME);
        parameters.put("theme_desc", THEME_DESC);
        parameters.put("theme_price", THEME_PRICE);
        return new SimpleJdbcInsert(jdbcTemplate).executeAndReturnKey(parameters).longValue();
    }

    @Override
    public ReservationResponseDTO findById(Long id) {
        String sql = "SELECT * from reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new ReservationResponseDTO(rs.getLong("id"), rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(), rs.getString("name"), new Theme(rs.getString("theme_name"),
                        rs.getString("theme_desc"), rs.getInt("theme_price"))), id);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        jdbcTemplate.update("DELETE FROM RESERVATION WHERE id = ?", id);
    }


}
