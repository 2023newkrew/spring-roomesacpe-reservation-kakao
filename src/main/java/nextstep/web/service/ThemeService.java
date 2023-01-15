package nextstep.web.service;

import lombok.RequiredArgsConstructor;
import nextstep.domain.Theme;
import nextstep.web.dto.ThemeRequestDto;
import nextstep.web.dto.ThemeResponseDto;
import nextstep.web.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThemeService {

    private final ThemeRepository themeRepository;

    @Transactional
    public Long create(ThemeRequestDto themeRequestDto) {
        Theme theme = Theme.builder()
                .name(themeRequestDto.getName())
                .desc(themeRequestDto.getDesc())
                .price(themeRequestDto.getPrice())
                .build();

        return themeRepository.save(theme);
    }

    public List<ThemeResponseDto> readAll() {
        return themeRepository.findAll()
                .stream()
                .map(ThemeResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        themeRepository.deleteById(id);
    }
}
