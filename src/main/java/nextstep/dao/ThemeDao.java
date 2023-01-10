package nextstep.dao;

import nextstep.domain.Themes;
import nextstep.entity.Theme;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDao {
    private final Themes themes = Themes.getInstance();

    public Theme findById(Long id) {
        return themes.findById(id);
    }
}
