package kakao.domain;

import domain.ThemeFactory;
import kakao.dto.request.CreateThemeRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ThemeFactoryTest {

    private final ThemeFactory themeFactory = new ThemeFactory();

    private final CreateThemeRequest request = new CreateThemeRequest("name", "desc", 1000);

    @DisplayName("CreateThemeRequest를 받아 Theme을 생성한다")
    @Test
    void createTheme() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> themeFactory.create(request));
    }
}
