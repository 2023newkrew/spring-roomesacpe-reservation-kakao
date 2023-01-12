package nextstep.repository;

import nextstep.domain.Theme;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ThemeJdbcDao implements ThemeDao{
    @Override
    public Long save(Theme theme) {
        return null;
    }

    @Override
    public Optional<Theme> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Theme> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Theme> findAll() {
        return null;
    }

    @Override
    public void update(Theme theme) {

    }

    @Override
    public void delete(Long id) {

    }
}
