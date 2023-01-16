package nextstep.theme.repository;

import nextstep.theme.domain.Theme;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

public interface ThemeRepository {

    Theme insert(Theme theme) throws DuplicateKeyException;

    Theme getById(Long id);

    List<Theme> getAll();

    Theme update(Theme theme) throws DuplicateKeyException;

    boolean delete(Long id);
}
