package roomescape;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import roomescape.domain.Theme;
import roomescape.repository.ThemeRepository;

@Component
public class DataLoader implements ApplicationRunner {
    public static Theme WANNA_GO_HOME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private final ThemeRepository themeRepository;

    public DataLoader(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Long themeId = themeRepository.insertTheme(WANNA_GO_HOME);
        WANNA_GO_HOME.setId(themeId);
    }
}
