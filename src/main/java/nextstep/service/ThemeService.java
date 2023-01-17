package nextstep.service;

import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.entity.Theme;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface ThemeService {


    Theme createTheme(ThemeCreateDto themeCreateDto);

    boolean editTheme(ThemeEditDto themeEditDto);

    @Transactional(readOnly = true)
    Theme findById(Long id);

    boolean deleteById(Long id);
}
