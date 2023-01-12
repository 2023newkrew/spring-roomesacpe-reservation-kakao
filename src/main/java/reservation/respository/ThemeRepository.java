package reservation.respository;

import reservation.model.domain.Reservation;
import reservation.model.domain.Theme;

public interface ThemeRepository {

    Long save(Theme theme);
}
