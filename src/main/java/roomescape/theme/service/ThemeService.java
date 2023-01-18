package roomescape.theme.service;

import org.springframework.stereotype.Service;
import roomescape.theme.repository.ThemeRepository;
import roomescape.theme.domain.Theme;
import roomescape.theme.dto.ThemeDto;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long addTheme(ThemeDto themeDto) {
        if (checkExistence(themeDto.getName())) throw new IllegalArgumentException("이미 있는 테마 이름");
        return themeRepository.add(new Theme(themeDto)).getId();
    }

    public List<Theme> getAllTheme() {
        return themeRepository.get();
    }

    public void removeTheme(Long id) {
        if (!checkExistence(id)) throw new IllegalArgumentException("없는 테마임");
        themeRepository.remove(id);
    }

    private boolean checkExistence(String name) {
        return themeRepository.get(name) != null;
    }

    private boolean checkExistence(Long id) {
        return themeRepository.get(id) != null;
    }
}
