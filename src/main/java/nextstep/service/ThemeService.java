package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.ThemeRequestDTO;

import java.util.List;

public interface ThemeService {
    Long create(ThemeRequestDTO request);

    List<Theme> getList();

    void deleteById(Long id);
}
