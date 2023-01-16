package nextstep.domain.theme.service;

import nextstep.domain.theme.domain.Theme;
import nextstep.domain.theme.dto.ThemeRequestDto;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.global.exceptions.exception.DuplicatedNameThemeException;
import nextstep.global.exceptions.exception.ThemeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long add(ThemeRequestDto themeRequestDto) {
        if (themeRepository.findByName(themeRequestDto.getName()).isPresent()) {
            throw new DuplicatedNameThemeException();
        }

        Theme theme = new Theme(
                themeRequestDto.getName(),
                themeRequestDto.getDesc(),
                themeRequestDto.getPrice()
        );
        return themeRepository.save(theme);
    }

    public Theme retrieve(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(ThemeNotFoundException::new);
    }

    public List<Theme> retrieveAll() {
        return themeRepository.findAll();
    }

    public void update(Long id, ThemeRequestDto themeRequestDto) {
        themeRepository.findByName(themeRequestDto.getName())
                .ifPresent(v -> {
                    if (!id.equals(v.getId())) {
                        throw new DuplicatedNameThemeException();
                    }
                });

        Theme theme = themeRepository.findById(id)
                .orElseThrow(ThemeNotFoundException::new);

        theme.setName(themeRequestDto.getName());
        theme.setDesc(themeRequestDto.getDesc());
        theme.setPrice(themeRequestDto.getPrice());
        themeRepository.update(theme);
    }

    public void delete(Long id) {
        themeRepository.findById(id)
                .orElseThrow(ThemeNotFoundException::new);

        themeRepository.delete(id);
    }
}

