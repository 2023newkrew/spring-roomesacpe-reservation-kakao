package kakao.service;

import kakao.dto.request.CreateThemeRequest;
import kakao.dto.request.UpdateThemeRequest;
import kakao.dto.response.ThemeResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class ThemeServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ThemeService themeService;

    private final CreateThemeRequest request = new CreateThemeRequest("name", "desc", 1000);

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE theme");
        jdbcTemplate.execute("ALTER TABLE theme ALTER COLUMN id RESTART WITH 1");
    }

    @DisplayName("CreateThemeRequest를 받아 새 Theme을 저장하고 해당 id를 반환한다")
    @Test
    void createTheme() {
        Assertions.assertThat(themeService.createTheme(request)).isOne();
    }

    @DisplayName("id에 해당하는 Theme의 Response를 반환한다")
    @Test
    void getTheme() {
        themeService.createTheme(request);

        ThemeResponse themeResponse = themeService.getTheme(1L);

        Assertions.assertThat(themeResponse.name).isEqualTo(request.name);
        Assertions.assertThat(themeResponse.desc).isEqualTo(request.desc);
        Assertions.assertThat(themeResponse.price).isEqualTo(request.price);
    }

    @DisplayName("UpdateThemeRequest의 내용에 따라 Theme을 업데이트하고 업데이트된 Theme의 Response을 반환한다")
    @Test
    void updateTheme() {
        themeService.createTheme(request);

        UpdateThemeRequest updateRequest = UpdateThemeRequest.builder()
                .id(1L)
                .name("updated")
                .desc("updated")
                .price(3000)
                .build();

        ThemeResponse response = themeService.updateTheme(updateRequest);

        Assertions.assertThat(response.name).isEqualTo("updated");
        Assertions.assertThat(response.desc).isEqualTo("updated");
        Assertions.assertThat(response.price).isEqualTo(3000);
    }

    @DisplayName("id에 해당하는 Theme을 삭제한다, 성공하면 1 실패하면 0을 반환한다")
    @Test
    void delete() {
        themeService.createTheme(request);

        Assertions.assertThat(themeService.delete(1L)).isOne();
        Assertions.assertThat(themeService.delete(1L)).isZero();
    }
}
