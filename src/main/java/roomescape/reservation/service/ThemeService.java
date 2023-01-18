package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.exception.BusinessException;
import roomescape.exception.ErrorCode;
import roomescape.reservation.repository.common.ThemeRepository;
import roomescape.reservation.domain.Theme;
import roomescape.reservation.dto.ThemeDto;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long addTheme(ThemeDto themeDto) {
        if (checkExistence(themeDto.getName()))
            throw new BusinessException(ErrorCode.DUPLICATE_THEME);
        return themeRepository.add(new Theme(themeDto)).getId();
    }

    public List<Theme> getAllTheme() {
        return themeRepository.get();
    }

    public void removeTheme(Long id) {
        if (!checkExistence(id))
            throw new BusinessException(ErrorCode.NOT_FOUND_THEME);
        themeRepository.remove(id);
    }

    private boolean checkExistence(String name) {
        return themeRepository.get(name) != null;
    }

    private boolean checkExistence(Long id) {
        return themeRepository.get(id) != null;
    }
}
