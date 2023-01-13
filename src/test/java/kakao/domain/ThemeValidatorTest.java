package kakao.domain;

import domain.Reservation;
import domain.Theme;
import domain.ThemeValidator;
import kakao.Initiator;
import kakao.dto.request.CreateThemeRequest;
import kakao.error.exception.DuplicatedThemeException;
import kakao.error.exception.UsingThemeException;
import kakao.repository.ReservationJDBCRepository;
import kakao.repository.ThemeJDBCRepository;
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
public class ThemeValidatorTest {
    private final ThemeJDBCRepository themeJDBCRepository;
    private final ReservationJDBCRepository reservationJDBCRepository;
    private final ThemeValidator themeValidator;
    private final Initiator initiator;

    @Autowired
    ThemeValidatorTest(JdbcTemplate jdbcTemplate, ThemeJDBCRepository themeJDBCRepository, ReservationJDBCRepository reservationJDBCRepository) {
        this.themeJDBCRepository = themeJDBCRepository;
        this.reservationJDBCRepository = reservationJDBCRepository;
        initiator = new Initiator(jdbcTemplate);
        themeValidator = new ThemeValidator(themeJDBCRepository, reservationJDBCRepository);
    }

    @BeforeEach
    void setUp() {
        initiator.createThemeForTest();
    }

    @AfterEach
    void clear() {
        initiator.clear();
    }


    @DisplayName("이미 예약된 테마에는 update 하면 UsingTheme 예외를 발생한다")
    @Test
    void updateUsedTheme() {
        reservationJDBCRepository.save(Reservation.builder()
                .name("baker")
                .date(LocalDate.of(2022, 10, 13))
                .time(LocalTime.of(13, 00))
                .theme(new Theme(1L, "워너고홈", "병맛 어드벤처 회사 코믹물", 29000))
                .build());

        Assertions.assertThatExceptionOfType(UsingThemeException.class)
                .isThrownBy(() -> themeValidator.validateForUpdate(1L));
    }

    @DisplayName("이미 존재하는 name의 CreateThemeRequest의 경우 DuplicateTheme 예외를 발생한다")
    @Test
    void createDuplicateNamedTheme() {
        Assertions.assertThatExceptionOfType(DuplicatedThemeException.class)
                .isThrownBy(() -> themeValidator.validateForCreate(new CreateThemeRequest(
                        "워너고홈",
                        "desc",
                        1000
                )));
    }
}
