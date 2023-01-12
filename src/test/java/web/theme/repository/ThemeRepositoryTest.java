package web.theme.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import web.entity.Theme;
import web.theme.exception.ErrorCode;
import web.theme.exception.ThemeException;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThemeRepositoryTest {

    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:schema.sql")
            .build();

    private ThemeRepository themeRepository = new ThemeRepository(dataSource);


    @AfterEach
    public void clearAll() {
        themeRepository.clearAll();
    }

    @Nested
    class Save {

        @Test
        void should_successfully_when_validReservation() {
            String name = "테마이름";
            String desc = "테마설명";
            int price = 22000;
            Theme theme = Theme.of(null, name, desc, price);

            long themeId = themeRepository.save(theme);

            Theme savedTheme = themeRepository.findById(themeId).orElseThrow();
            assertThat(savedTheme.getName()).isEqualTo(name);
            assertThat(savedTheme.getDesc()).isEqualTo(desc);
            assertThat(savedTheme.getPrice()).isEqualTo(price);
        }

        @Test
        void should_throwException_when_saveDuplicateTheme() {
            String name = "테마이름";
            String desc = "테마설명";
            int price = 22000;
            Theme theme = Theme.of(null, name, desc, price);

            themeRepository.save(theme);

            assertThatThrownBy(() -> themeRepository.save(theme))
                    .isInstanceOf(ThemeException.class)
                    .hasMessage(ErrorCode.THEME_DUPLICATE.getMessage());
        }
    }
}