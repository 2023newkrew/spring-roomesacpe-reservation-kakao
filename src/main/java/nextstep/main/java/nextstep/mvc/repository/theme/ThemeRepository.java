package nextstep.main.java.nextstep.mvc.repository.theme;

import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateOrUpdateRequest;
import nextstep.main.java.nextstep.mvc.repository.CrudRepository;

public interface ThemeRepository extends CrudRepository<ThemeCreateOrUpdateRequest, Theme> {
}
