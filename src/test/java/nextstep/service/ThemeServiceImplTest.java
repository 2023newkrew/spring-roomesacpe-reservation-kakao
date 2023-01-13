package nextstep.service;

import static org.assertj.core.api.Assertions.assertThat;

import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeResponseDto;
import nextstep.repository.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ThemeServiceImplTest {

    @Autowired
    ThemeService themeService;
    @Autowired
    ThemeRepository themeRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    final String NAME = "NAME";
    final String DESC = "DESC";
    final Integer PRICE = 1234;
    Long id = null;

    @BeforeEach
    void init() {
        jdbcTemplate.update("DELETE FROM THEME");
        id = themeRepository.save(new ThemeCreateDto(NAME, DESC, PRICE));

    }

    @Test
    void create_theme_test() {
        themeService.createTheme(new ThemeCreateDto("name1", "desc", 1234));
        Long count = jdbcTemplate.query("SELECT COUNT(*) FROM THEME;", (rs, rowNum) -> rs.getLong(1)).get(0);
        assertThat(count).isEqualTo(2);
    }

    @Test
    void find_theme_test() {
        ThemeResponseDto themeResponseDto = themeService.findTheme(id);
        assertThat(themeResponseDto.getName()).isEqualTo(NAME);
        assertThat(themeResponseDto.getDescription()).isEqualTo(DESC);
        assertThat(themeResponseDto.getPrice()).isEqualTo(PRICE);
    }

    @Test
    void delete_theme_test(){
        themeService.deleteById(id);
        Long count = jdbcTemplate.query("SELECT COUNT(*) FROM THEME;", (rs, rowNum) -> rs.getLong(1)).get(0);
        assertThat(count).isEqualTo(0);
    }


}