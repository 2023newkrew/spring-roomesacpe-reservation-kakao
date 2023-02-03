package nextstep.repository.repositoryImpl;

import lombok.AllArgsConstructor;
import nextstep.domain.Theme;
import nextstep.dao.ThemeDAO;
import nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class ThemeRepositoryImpl implements ThemeRepository {
    private final ThemeDAO dao;

    @Override
    public Long insert(Theme theme) {
        return dao.insert(theme);
    }

    @Override
    public List<Theme> getList() {
        return dao.getList();
    }

    @Override
    public Theme getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return dao.deleteById(id);
    }
}
