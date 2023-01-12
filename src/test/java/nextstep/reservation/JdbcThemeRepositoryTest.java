package nextstep.reservation;

import nextstep.reservation.entity.Theme;
import nextstep.reservation.repository.JdbcThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JdbcThemeRepositoryTest {

    @Autowired
    private JdbcThemeRepository jdbcThemeRepository;
    private Theme theme;

    @BeforeEach
    void setUp() {
        this.theme = new Theme(null, "호러", "매우 무서운", 24000);
    }

    @Test
    @DisplayName("생성")
    void create() {
        //given

        //when
        Theme created = jdbcThemeRepository.create(theme);

        //then
        assertThat(themeTestEquals(theme, created)).isTrue();
    }


    @Test
    @DisplayName("전체 조회")
    void findAll() {
        //given
        Theme created = jdbcThemeRepository.create(theme);
        Theme created2 = jdbcThemeRepository.create(theme);

        //when
        List<Theme> founded = jdbcThemeRepository.findAll();

        //then
        assertThat(founded).isEqualTo(List.of(created, created2));
    }

    @Test
    @DisplayName("id를 통한 삭제")
    void deleteById() {
        //given
        Theme created = jdbcThemeRepository.create(theme);

        //when
        int result = jdbcThemeRepository.deleteById(created.getId());

        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("테이블 전체 삭제")
    void clear() {
        //given
        Theme created = jdbcThemeRepository.create(theme);
        Theme created2 = jdbcThemeRepository.create(theme);

        //when
        jdbcThemeRepository.clear();

        //then
        int size = jdbcThemeRepository.findAll().size();
        assertThat(size).isEqualTo(0);

    }

    private boolean themeTestEquals(Theme a, Theme b) {
        return a.getName().equals(b.getName()) &&
                a.getDesc().equals(b.getDesc()) &&
                a.getPrice().equals(b.getPrice());
    }
}
