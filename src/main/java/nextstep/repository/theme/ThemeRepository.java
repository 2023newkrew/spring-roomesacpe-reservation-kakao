package nextstep.repository.theme;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

public interface ThemeRepository {
    Long save(String name, String desc, Integer price);

    Long save(Theme theme);

    Reservation findById(Long id);

    void deleteById(Long id);

    void createTable();

    void dropTable();
}