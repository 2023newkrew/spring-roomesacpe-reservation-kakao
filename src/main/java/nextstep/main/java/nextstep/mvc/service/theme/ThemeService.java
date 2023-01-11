package nextstep.main.java.nextstep.mvc.service.theme;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchThemeException;
import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.domain.theme.ThemeMapper;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeUpdateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.response.ThemeFindResponse;
import nextstep.main.java.nextstep.mvc.repository.theme.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    public Long save(ThemeCreateRequest request) {
        return themeRepository.save(request);
    }

    public ThemeFindResponse findById(Long id) {
        return themeMapper.themeToFindResponse(
            themeRepository.findById(id)
                .orElseThrow(NoSuchThemeException::new)
        );
    }

    public List<ThemeFindResponse> findAll() {
        return themeRepository.findAll()
                .stream()
                .map(themeMapper::themeToFindResponse)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        themeRepository.deleteById(id);
    }

    public void update(Long id, ThemeUpdateRequest request) {
        themeRepository.update(id, request);
    }
}
