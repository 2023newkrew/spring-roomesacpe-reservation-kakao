package nextstep.theme.service;

import nextstep.theme.dto.ThemeDetail;
import nextstep.theme.dto.ThemeDto;

import java.util.List;

public interface ThemeService {
    ThemeDetail findById(Long id);
    List<ThemeDetail> findAll();

    Long save(ThemeDto themeDto);
    int update(ThemeDto themeDto);
    void deleteById(Long id);
}
