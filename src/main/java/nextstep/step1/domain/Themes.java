package nextstep.step1.domain;


import nextstep.step1.entity.Theme;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Themes {
    public static final Long THEME_ID_INITIAL_VALUE = 1L;
    private final Map<Long, Theme> themes;
    private final AtomicLong idAtomicLong;

    private static final Themes instance = new Themes();

    private Themes(){
        this.themes = new ConcurrentHashMap<>();
        this.idAtomicLong = new AtomicLong(THEME_ID_INITIAL_VALUE);
        add(new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000));
    }

    public static Themes getInstance(){
        return instance;
    }

    public void add(Theme theme) {
        theme.setId(getAutoIncrementId());
        themes.put(theme.getId(), theme);
    }

    private Long getAutoIncrementId(){
        return idAtomicLong.getAndIncrement();
    }

    public Theme findById(Long id) {
        return themes.values().stream()
                .filter(theme -> id.equals(theme.getId()))
                .findFirst()
                .orElse(null);
    }
}
