package roomescape.repository.Reservation;

import org.springframework.jdbc.support.KeyHolder;

import java.util.Objects;

public class H2JdbcDriver {

    public Long createReserve(KeyHolder keyHolder){
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
