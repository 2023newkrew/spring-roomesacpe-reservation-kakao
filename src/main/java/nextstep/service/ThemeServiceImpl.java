package nextstep.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.entity.Theme;
import nextstep.repository.ThemeRepository;

@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    @Override
    public Theme createTheme(ThemeCreateDto themeCreateDto){
        return themeRepository.save(themeCreateDto.toEntity());
    }

    @Override
    public boolean editTheme(ThemeEditDto themeEditDto) {
        return themeRepository.update(themeEditDto.toEntity()) != 0;
    }

    @Override
    public Theme findById(Long id) {
        Theme theme = themeRepository.findById(id).orElse(null);
        if(Objects.isNull(theme)){
            return null;
        }
        return theme;
    }

    @Override
    public boolean deleteById(Long id) {
        return themeRepository.deleteById(id) != 0;
    }
}
