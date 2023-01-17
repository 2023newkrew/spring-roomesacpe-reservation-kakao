package roomescape.repository.DBMapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;

public interface DatabaseMapper {
    Long getAutoKey(KeyHolder keyHolder);
    RowMapper<Reservation> reservationRowMapper(Long reservationId);
    RowMapper<Theme> themeRowMapper(Long themeId);
}
