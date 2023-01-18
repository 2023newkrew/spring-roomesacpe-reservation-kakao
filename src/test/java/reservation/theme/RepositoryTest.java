package reservation.theme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import reservation.model.domain.Theme;
import reservation.model.dto.RequestTheme;
import reservation.respository.ThemeJdbcTemplateRepository;


import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
@JdbcTest
public class RepositoryTest {

    private ThemeJdbcTemplateRepository themeRepository;
    private RequestTheme requestTheme;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RepositoryTest() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        LocalTime time = LocalTime.of(11, 0);

        requestTheme = new RequestTheme("name", "desc", 1000);
    }

    @BeforeEach
    void setUp() {
        themeRepository = new ThemeJdbcTemplateRepository(jdbcTemplate, jdbcTemplate.getDataSource());
    }

    @Test
    @DisplayName("테마 생성이 되어야 한다.")
    void save() {
        Long id = themeRepository.save(makeTheme(1L, requestTheme));
        assertThat(id).isGreaterThan(0);
    }

    @Test
    @DisplayName("생성된 테마 목록을 조회할 수 있어야 한다.")
    void find() {
        Long id = themeRepository.save(makeTheme(1L, requestTheme));
        Theme before = makeTheme(id, requestTheme);
        Theme after = themeRepository.findById(id);
        assertThat(before).isEqualTo(after);
    }

    @Test
    @DisplayName("생성된 예약을 취소할 수 있어야 한다.")
    void delete() {
        Long id = themeRepository.save(makeTheme(1L, requestTheme));
        int rowCount = themeRepository.deleteById(id);
        assertThat(rowCount).isEqualTo(1);
    }

    @Test
    @DisplayName("이미 존재하는 테마는 생성이 불가능하다.")
    void duplicate(){
        Long id = themeRepository.save(makeTheme(1L, requestTheme));
        assertThat(themeRepository.checkExistById(id))
                .isTrue();
    }

    private Theme makeTheme(Long id, RequestTheme req){
        return new Theme(id, req.getName(), req.getDesc(), req.getPrice());
    }
}