package kakao.repository;

import domain.Reservation;
import domain.Theme;
import kakao.Initiator;
import kakao.dto.request.UpdateThemeRequest;
import kakao.error.exception.RecordNotFoundException;
import kakao.error.exception.UsingThemeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
class ThemeJDBCRepositoryTest {

    Initiator initiator;

    @Autowired
    private ThemeJDBCRepository themeJDBCRepository;
    @Autowired
    private ReservationJDBCRepository reservationJDBCRepository;

    private final Theme themeModel = new Theme("name", "desc", 1000);

    @Autowired
    ThemeJDBCRepositoryTest(JdbcTemplate jdbcTemplate) {
        initiator = new Initiator(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void clear() {
        initiator.clear();
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
        Assertions.assertThat(themeJDBCRepository.update(request.getUpdateSQL())).isOne();

        Theme cp = themeJDBCRepository.findById(1L);
        Assertions.assertThat(cp.getName()).isEqualTo("updatedName");
        Assertions.assertThat(cp.getDesc()).isEqualTo("updatedDesc");
        Assertions.assertThat(cp.getPrice()).isEqualTo(3000);
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
