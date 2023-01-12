package nextstep.step1.dao;

import nextstep.step1.domain.Themes;
import nextstep.step1.entity.Theme;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDao {
    private final Themes themes = Themes.getInstance();

    public Theme findById(Long id) {
        return themes.findById(id);
    }
}
