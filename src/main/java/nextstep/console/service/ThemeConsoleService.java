package nextstep.console.service;

import nextstep.console.repository.ThemeConnRepository;
import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;
import nextstep.domain.service.ThemeService;
import nextstep.domain.dto.ThemeRequest;
import nextstep.domain.dto.ThemeResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ThemeConsoleService implements ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeConsoleService() {
        this.themeRepository = new ThemeConnRepository();
    }

    public Theme newTheme(ThemeRequest themeRequest){
        Theme theme = new Theme(themeRequest.getName(),themeRequest.getDesc(),themeRequest.getPrice());
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