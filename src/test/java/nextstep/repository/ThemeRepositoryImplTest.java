package nextstep.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.entity.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@SpringBootTest
class ThemeRepositoryImplTest {

    @Autowired
    ThemeRepository themeRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    Long id = 0L;
    final String DESC = "DESC";
    final Integer PRICE = 122234;
    final String NAME = "NAME";


    @BeforeEach
    void init() {
        jdbcTemplate.update("DELETE FROM THEME");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("desc", DESC);
        parameters.put("name", NAME);
        parameters.put("price", PRICE);
        id = new SimpleJdbcInsert(jdbcTemplate).withTableName("THEME").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(parameters).longValue();
    }


    @Test
    void save_요청시_저장한다() {
        Theme theme = themeRepository.save(new ThemeCreateDto("NAME", "DESC", 1234).toEntity());
        assertThat(theme.getId()).isEqualTo(id + 1);
    }

    @Test
    void find_by_id테스트() {
        Theme theme = themeRepository.findById(id).get();
        assertThat(theme.getId()).isEqualTo(id);
        assertThat(theme.getPrice()).isEqualTo(PRICE);
        assertThat(theme.getName()).isEqualTo(NAME);
        assertThat(theme.getDescription()).isEqualTo(DESC);
    }

    @Test
    void update_테스트() {
        ThemeEditDto themeEditDto = new ThemeEditDto(id, "NAME2", "DESC2", 122);
        themeRepository.update(themeEditDto.toEntity());
        Theme theme = themeRepository.findById(id).get();
        assertThat(theme.getId()).isEqualTo(id);
        assertThat(theme.getPrice()).isEqualTo(122);
        assertThat(theme.getName()).isEqualTo("NAME2");
        assertThat(theme.getDescription()).isEqualTo("DESC2");
    }

    @Test
    void delete_by_id_테스트() {
        themeRepository.deleteById(id);
        assertThat(themeRepository.findById(id).isEmpty()).isTrue();

    }

}