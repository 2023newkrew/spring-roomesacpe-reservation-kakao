package nextstep.domain.service;

import nextstep.controller.exception.DataNotFoundException;
import nextstep.domain.theme.Theme;
import nextstep.repository.WebAppThemeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ThemeService {
    private final WebAppThemeRepo repo;

    @Autowired
    public ThemeService(WebAppThemeRepo repo) {
        this.repo = repo;
    }

    public long save(Theme theme) {
        return repo.save(theme);
    }

    public Theme find(long id) {
        Theme theme = repo.findById(id);
        if (theme == null) {
            throw new DataNotFoundException();
        }
        return theme;
    }

    public List<Theme> findAll() {
        List<Theme> themes = repo.findAll();
        return themes;
    }

    public void delete(long id) {
        if (repo.delete(id) == 0) {
            throw new DataNotFoundException();
        }
    }
}
