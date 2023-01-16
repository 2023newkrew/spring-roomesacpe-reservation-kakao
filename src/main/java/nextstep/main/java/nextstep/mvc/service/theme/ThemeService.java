package nextstep.main.java.nextstep.mvc.service.theme;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.global.exception.exception.AlreadyReservedThemeException;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchThemeException;
import nextstep.main.java.nextstep.mvc.domain.theme.ThemeMapper;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateOrUpdateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.response.ThemeFindResponse;
import nextstep.main.java.nextstep.mvc.repository.theme.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    @Transactional
    public Long save(ThemeCreateOrUpdateRequest request) {
        return themeRepository.save(request);
    }

    @Transactional(readOnly = true)
    public ThemeFindResponse findById(Long id) {
        return themeMapper.themeToFindResponse(
            themeRepository.findById(id)
                .orElseThrow(NoSuchThemeException::new)
        );
    }

    @Transactional(readOnly = true)
    public List<ThemeFindResponse> findAll() {
        return themeRepository.findAll()
                .stream()
                .map(themeMapper::themeToFindResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id, Boolean reserved) {
        checkIsReserved(reserved);
        checkIsExists(id);
        themeRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, ThemeCreateOrUpdateRequest request) {
        themeRepository.update(id, request);
    }

    private void checkIsReserved(Boolean reserved) {
        if (reserved) {
            throw new AlreadyReservedThemeException();
        }
    }

    private void checkIsExists(Long id) {
        if (!themeRepository.existsById(id)) {
            throw new NoSuchThemeException();
        }
    }
}
