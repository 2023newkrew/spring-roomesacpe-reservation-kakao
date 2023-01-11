package kakao.repository;

import domain.Theme;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeRepository {
    public final static Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
}
