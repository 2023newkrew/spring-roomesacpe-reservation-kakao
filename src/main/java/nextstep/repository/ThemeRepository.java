package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ThemeRepository {
    Theme create(Theme theme);

    Theme find(long id);

    List<Theme> findAll();

    boolean delete(long id);

}
