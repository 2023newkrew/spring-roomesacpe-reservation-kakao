package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exception.ReservationException;
import nextstep.reservation.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static nextstep.reservation.exception.ReservationExceptionCode.NO_SUCH_THEME;

@Service
@RequiredArgsConstructor
@Transactional
public class ThemeService {
    private final ThemeRepository themeRepository;

    public Theme create(Theme theme) {
        return themeRepository.save(theme);
    }

    @Transactional(readOnly = true)
    public Theme findById(long id) {
        Optional<Theme> foundedThemeOptional = themeRepository.findById(id);
        if (foundedThemeOptional.isEmpty()) {
            throw new ReservationException(NO_SUCH_THEME);
        }
        return foundedThemeOptional.get();
    }

    @Transactional(readOnly = true)
    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public boolean deleteById(long id) {
        int deletedRowNumber = themeRepository.deleteById(id);
        return deletedRowNumber == 1;
    }

    public void clear() {
        themeRepository.clear();
    }

}
