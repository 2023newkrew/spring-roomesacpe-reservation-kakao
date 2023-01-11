package nextstep.main.java.nextstep.mvc.service.theme;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.domain.theme.ThemeMapper;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateRequest;
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

    public Theme findByName(String name) {
        return themeRepository.findByName(name)
                .orElseThrow(RuntimeException::new);
    }

    public ThemeFindResponse findById(Long id) {
        return themeMapper.themeToFindResponse(
            themeRepository.findById(id)
                .orElseThrow(RuntimeException::new)
        );
    }

    public List<ThemeFindResponse> findAll() {
        return themeRepository.findAll()
                .stream()
                .map(themeMapper::themeToFindResponse)
                .collect(Collectors.toList());
    }
}
