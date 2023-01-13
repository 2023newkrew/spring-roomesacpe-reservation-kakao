package nextstep.service;

import java.sql.SQLException;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.dto.ThemeResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface ThemeService {


    Long createTheme(ThemeCreateDto themeCreateDto);

    boolean editTheme(ThemeEditDto themeEditDto);

    @Transactional(readOnly = true)
    ThemeResponseDto findTheme(Long id);

    boolean deleteById(Long id);
}
