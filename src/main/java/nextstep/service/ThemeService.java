package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.ThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme newTheme(ThemeRequest themeRequest){
        Theme theme = new Theme();
        theme.setName(themeRequest.getName());
        theme.setDesc(themeRequest.getDesc());
        theme.setPrice(themeRequest.getPrice());
        return this.themeRepository.create(theme);
    }

    public ThemeResponse findTheme(long id){
        Theme result = this.themeRepository.find(id);
        return new ThemeResponse(result.getId(),result.getName(),result.getDesc(),result.getPrice());
    }

    public List<ThemeResponse> findAllTheme(){

        return this.themeRepository.findAll()
                .stream()
                .map(e->new ThemeResponse(e.getId(),e.getName(),e.getDesc(),e.getPrice()))
                .collect(Collectors.toList());
    }

    public boolean deleteTheme(long id){
        return this.themeRepository.delete(id);
    }
}
