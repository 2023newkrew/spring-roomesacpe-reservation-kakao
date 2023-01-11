package nextstep.main.java.nextstep.service.theme;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.domain.theme.Theme;
import nextstep.main.java.nextstep.domain.theme.ThemeCreateRequestDto;
import nextstep.main.java.nextstep.repository.theme.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public Long save(ThemeCreateRequestDto request) {
        return themeRepository.save(request);
    }

    public Theme findByName(String name) {
        return themeRepository.findByName(name).orElseThrow(RuntimeException::new);
    }
}
