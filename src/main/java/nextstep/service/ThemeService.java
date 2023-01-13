package nextstep.service;

import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.entity.Theme;
import nextstep.dto.ThemeResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface ThemeService {


    Theme createTheme(ThemeCreateDto themeCreateDto);

    boolean editTheme(ThemeEditDto themeEditDto);

    @Transactional(readOnly = true)
    ThemeResponseDto findTheme(Long id);

    void deleteById(Long id);
}
