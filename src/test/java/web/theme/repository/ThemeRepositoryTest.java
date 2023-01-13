package web.theme.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import web.entity.Theme;
import web.exception.ErrorCode;
import web.theme.exception.ThemeException;

import javax.sql.DataSource;
import java.util.List;

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
        void should_successfully_when_validTheme() {
            String name = "테마이름";
            String desc = "테마설명";
            int price = 22000;
            Theme theme = Theme.builder()
                    .name(name)
                    .desc(desc)
                    .price(price)
                    .build();

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
            Theme theme = Theme.builder()
                    .name(name)
                    .desc(desc)
                    .price(price)
                    .build();

            themeRepository.save(theme);

            assertThatThrownBy(() -> themeRepository.save(theme))
                    .isInstanceOf(ThemeException.class)
                    .hasMessage(ErrorCode.THEME_DUPLICATE.getMessage());
        }
    }

    @Nested
    class FindAll {

        @Test
        void should_successfully_when_multiThemes() {
            String name = "테마이름";
            String desc = "테마설명";
            int price = 22000;
            Theme theme = Theme.builder()
                    .name(name)
                    .desc(desc)
                    .price(price)
                    .build();
            long themeId = themeRepository.save(theme);

            String name2 = "테마이름2";
            String desc2 = "테마설명2";
            int price2 = 45000;
            Theme theme2 = Theme.builder()
                    .name(name2)
                    .desc(desc2)
                    .price(price2)
                    .build();
            long themeId2 = themeRepository.save(theme2);

            List<Theme> findThemes = themeRepository.findAll();

            assertThat(findThemes.get(0).getId()).isEqualTo(themeId);
            assertThat(findThemes.get(0).getName()).isEqualTo(name);
            assertThat(findThemes.get(0).getDesc()).isEqualTo(desc);
            assertThat(findThemes.get(0).getPrice()).isEqualTo(price);
            assertThat(findThemes.get(1).getId()).isEqualTo(themeId2);
            assertThat(findThemes.get(1).getName()).isEqualTo(name2);
            assertThat(findThemes.get(1).getDesc()).isEqualTo(desc2);
            assertThat(findThemes.get(1).getPrice()).isEqualTo(price2);
        }

        @Test
        void should_returnEmptyList_when_notExistTheme() {
            assertThat(themeRepository.findAll()).isNotNull();
            assertThat(themeRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class Delete {

        @Test
        void should_successfully_when_existTheme() {
            String name = "테마이름";
            String desc = "테마설명";
            int price = 22000;
            Theme theme = Theme.builder()
                    .name(name)
                    .desc(desc)
                    .price(price)
                    .build();
            long themeId = themeRepository.save(theme);

            long deleteThemeCount = themeRepository.delete(themeId);
            assertThat(deleteThemeCount).isEqualTo(1L);
            assertThat(themeRepository.findById(themeId)).isEmpty();
        }

        @Test
        void should_return0_when_notExistTheme() {
            assertThat(themeRepository.delete(-1)).isEqualTo(0L);
        }
    }
}