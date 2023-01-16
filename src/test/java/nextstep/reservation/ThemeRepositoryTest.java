package nextstep.reservation;

import nextstep.reservation.entity.Theme;
import nextstep.reservation.repository.JdbcThemeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static nextstep.reservation.constant.RoomEscapeConstant.DUMMY_ID;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ThemeRepositoryTest {

    @Autowired
    private JdbcThemeRepository jdbcThemeRepository;

    @Test
    @DisplayName("테마 생성")
    void create() {
        //given
        Theme theme = new Theme(DUMMY_ID, "호러", "매우 무서운", 24000);

        //when
        Theme created = jdbcThemeRepository.save(theme);

        //then
        assertThat(themeTestEquals(theme, created)).isTrue();
    }

    @Test
    @DisplayName("id를 통한 조회")
    void findById() {
        //given
        Theme theme = new Theme(DUMMY_ID, "호러", "매우 무서운", 24000);
        Theme created = jdbcThemeRepository.save(theme);

        //when
        Optional<Theme> foundedThemeOptional = jdbcThemeRepository.findById(created.getId());

        //then
        Assertions.assertThat(foundedThemeOptional.isPresent()).isTrue();
        Assertions.assertThat(foundedThemeOptional.get()).isEqualTo(created);
    }


    @Test
    @DisplayName("전체 조회시 리스트로 반환")
    void findAll() {
        //given
        Theme theme = new Theme(DUMMY_ID, "호러", "매우 무서운", 24000);
        Theme created = jdbcThemeRepository.save(theme);
        Theme created2 = jdbcThemeRepository.save(theme);

        //when
        List<Theme> founded = jdbcThemeRepository.findAll();

        //then
        assertThat(founded).isEqualTo(List.of(created, created2));
    }

    @Test
    @DisplayName("id를 통한 삭제 성공")
    void deleteById() {
        //given
        Theme theme = new Theme(DUMMY_ID, "호러", "매우 무서운", 24000);
        Theme created = jdbcThemeRepository.save(theme);

        //when
        int result = jdbcThemeRepository.deleteById(created.getId());

        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("테이블 전체 삭제")
    void clear() {
        //given
        Theme theme = new Theme(DUMMY_ID, "호러", "매우 무서운", 24000);
        Theme created = jdbcThemeRepository.save(theme);
        Theme created2 = jdbcThemeRepository.save(theme);

        //when
        jdbcThemeRepository.clear();

        //then
        int size = jdbcThemeRepository.findAll().size();
        assertThat(size).isEqualTo(0);

    }

    private boolean themeTestEquals(Theme a, Theme b) { //id를 제외한 Content 비교
        return a.getName().equals(b.getName()) &&
                a.getDesc().equals(b.getDesc()) &&
                a.getPrice().equals(b.getPrice());
    }
}
