package nextstep.domain;

import nextstep.dto.ThemeDto;
import nextstep.entity.Theme;

import java.util.LinkedList;
import java.util.List;

public class Themes {
    private final List<Theme> themes;
    private Long lastId;

    private static final Themes instance = new Themes();

    private Themes(){
        this.lastId = 1L;
        this.themes = new LinkedList<>();
        add(new ThemeDto("워너고홈", "병맛 어드벤처 회사 코믹물", 29000));
    }

    public static Themes getInstance(){
        return instance;
    }

    public void add(ThemeDto themeDto) {
        themeDto.setId(getAutoIncrementId());
        themes.add(new Theme(themeDto));
    }

    private Long getAutoIncrementId(){
        return lastId++;
    }

    public Theme findById(Long id) {
        return themes.stream()
                .filter(theme -> id.equals(theme.getId()))
                .findFirst()
                .orElse(null);
    }
}
