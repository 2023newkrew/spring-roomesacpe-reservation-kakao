package nextstep.reservation.service;

import nextstep.reservation.entity.Theme;
import nextstep.reservation.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme create(Theme theme) {
        return themeRepository.save(theme);
    }

    @Transactional(readOnly = true)
    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public boolean deleteById(Long id) {
        int deletedRowNumber = themeRepository.deleteById(id);
        return deletedRowNumber == 1;
    }

    public void clear() {
        themeRepository.clear();
    }
}
