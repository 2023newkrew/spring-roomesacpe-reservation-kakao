package kakao.repository;

import domain.Reservation;
import domain.Theme;
import kakao.dto.request.UpdateThemeRequest;
import kakao.error.exception.RecordNotFoundException;
import kakao.error.exception.UsingThemeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class ThemeJDBCRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ThemeJDBCRepository themeJDBCRepository;

    @Autowired
    private ReservationJDBCRepository reservationJDBCRepository;

    private final Theme themeModel = new Theme("name", "desc", 1000);

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE theme");
        jdbcTemplate.execute("ALTER TABLE theme ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    private void compareThemeWithoutId(Theme a, Theme b) {
        Assertions.assertThat(a.getName()).isEqualTo(b.getName());
        Assertions.assertThat(a.getDesc()).isEqualTo(b.getDesc());
        Assertions.assertThat(a.getPrice()).isEqualTo(b.getPrice());
    }

    @DisplayName("이름, 설명, 가격정보를 받아 새 Theme을 저장하고 해당하는 id를 반환한다")
    @Test
    void createTheme() {
        Assertions.assertThat(themeJDBCRepository.save(themeModel)).isOne();
    }

    @DisplayName("id에 해당하는 Theme을 조회한다")
    @Test
    void findById() {
        themeJDBCRepository.save(themeModel);
        Theme cp = themeJDBCRepository.findById(1L);
        compareThemeWithoutId(themeModel, cp);
    }

    @DisplayName("id에 해당하는 Theme이 존재하지 않으면 RecordNotFound Exception을 반환한다")
    @Test
    void findByInvalidId() {
        Assertions.assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> themeJDBCRepository.findById(1L));
    }

    @DisplayName("id에 해당하는 Theme의 name을 업데이트 하고, 업데이트된 count를 반환한다")
    @Test
    void updateNameById() {
        themeJDBCRepository.save(themeModel);
        Assertions.assertThat(themeJDBCRepository.updateName(1L, "updateName")).isOne();

        Theme updateTheme = themeJDBCRepository.findById(1L);
        Assertions.assertThat(updateTheme.getName()).isEqualTo("updateName");
    }

    @DisplayName("업데이트 하려는 중복된 name이 존재하면 업데이트 하지 않는다")
    @Test
    void updateDuplicate() {
        themeJDBCRepository.save(themeModel);
        themeJDBCRepository.save(new Theme("updateName", "desc", 1000));

        Assertions.assertThat(themeJDBCRepository.updateName(1L, "updateName")).isZero();
    }

    @DisplayName("id에 해당하는 Theme의 desc을 업데이트하고 업데이트된 count를 반환한다")
    @Test
    void updateDescById() {
        themeJDBCRepository.save(themeModel);

        Assertions.assertThat(themeJDBCRepository.updateDesc(1L, "updateDesc")).isOne();

        Assertions.assertThat(themeJDBCRepository.findById(1L).getDesc()).isEqualTo("updateDesc");
    }

    @DisplayName("id에 해당하는 Theme의 price를 업데이트하고 업데이트된 count를 반환한다")
    @Test
    void updatePriceById() {
        themeJDBCRepository.save(themeModel);

        Assertions.assertThat(themeJDBCRepository.updatePrice(1L, 2000)).isOne();

        Assertions.assertThat(themeJDBCRepository.findById(1L).getPrice()).isEqualTo(2000);
    }

    @DisplayName("UpdateThemeRequest에 해당하는 내용으로 업데이트하고 업데이트된 count를 반환한다")
    @Test
    void update() {
        UpdateThemeRequest request = UpdateThemeRequest.builder()
                .id(1L)
                .name("updatedName")
                .desc("updatedDesc")
                .price(3000)
                .build();

        themeJDBCRepository.save(themeModel);
        Assertions.assertThat(themeJDBCRepository.update(request)).isOne();

        Theme cp = themeJDBCRepository.findById(1L);
        Assertions.assertThat(cp.getName()).isEqualTo("updatedName");
        Assertions.assertThat(cp.getDesc()).isEqualTo("updatedDesc");
        Assertions.assertThat(cp.getPrice()).isEqualTo(3000);
    }

    @DisplayName("이미 예약된 테마를 update 하면 UsingTheme 예외를 발생한다")
    @Test
    void updateUsedTheme() {
        UpdateThemeRequest request = UpdateThemeRequest.builder()
                .id(1L)
                .name("updatedName")
                .desc("updatedDesc")
                .price(3000)
                .build();

        themeJDBCRepository.save(themeModel);
        reservationJDBCRepository.save(
                Reservation.builder()
                        .name("name")
                        .date(LocalDate.of(2022, 10, 13))
                        .time(LocalTime.of(13, 00))
                        .theme(themeJDBCRepository.findById(1L))
                        .build()
        );

        Assertions.assertThatExceptionOfType(UsingThemeException.class)
                .isThrownBy(() -> themeJDBCRepository.update(request));
    }

    @DisplayName("id에 해당하는 Theme을 삭제하고, 삭제된 count를 반환한다")
    @Test
    void delete() {
        themeJDBCRepository.save(themeModel);

        Assertions.assertThat(themeJDBCRepository.delete(1L)).isOne();
        Assertions.assertThat(themeJDBCRepository.delete(1L)).isZero();
    }

    @DisplayName("이미 예약된 theme을 삭제하면 UsingTheme 예외를 발생한다")
    @Test
    void deleteUsingTheme() {
        themeJDBCRepository.save(themeModel);
        reservationJDBCRepository.save(
                Reservation.builder()
                        .name("name")
                        .date(LocalDate.of(2022, 10, 13))
                        .time(LocalTime.of(13, 00))
                        .theme(themeJDBCRepository.findById(1L))
                        .build()
        );

        Assertions.assertThatExceptionOfType(UsingThemeException.class)
                .isThrownBy(() -> themeJDBCRepository.delete(1L));
    }
}
