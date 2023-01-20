package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import roomescape.connection.ConnectionManager;
import roomescape.connection.ConnectionSetting;
import roomescape.connection.PoolSetting;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationThemeIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.FindReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.InsertReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.RemoveReservationPreparedStatementCreator;
import roomescape.dao.theme.ThemeDAO;
import roomescape.dao.theme.preparedstatementcreator.ExistThemeIdPreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ExistThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.FindThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.InsertThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ListThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.RemoveThemePreparedStatementCreator;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;

@DisplayName("연결 매니저 테스트")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class DAOManagerTest {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final ConnectionSetting CONNECTION_SETTING = new ConnectionSetting(URL, USER, PASSWORD);
    private static final PoolSetting CONNECTION_POOL_SETTING = new PoolSetting(10);

    private static final LocalDate RESERVATION_DATE_DATA1 = LocalDate.parse("2022-08-01");
    private static final LocalDate RESERVATION_DATE_DATA2 = LocalDate.parse("2022-08-02");
    private static final LocalTime RESERVATION_TIME_DATA = LocalTime.parse("13:00");
    private static final String RESERVATION_NAME_DATA = "test";
    private static final Long RESERVATION_THEME_ID_DATA = 2L;

    private static final Reservation RESERVATION1 = new Reservation(
            RESERVATION_DATE_DATA1, RESERVATION_TIME_DATA,
            RESERVATION_NAME_DATA, RESERVATION_THEME_ID_DATA);

    private static final Reservation RESERVATION2 = new Reservation(
            RESERVATION_DATE_DATA2, RESERVATION_TIME_DATA,
            RESERVATION_NAME_DATA, RESERVATION_THEME_ID_DATA);

    private static final Long THEME_ID_DATA = 1L;
    private static final String THEME_NAME_DATA1 = "워너고홈";
    private static final String THEME_NAME_DATA2 = "테스트 이름";
    private static final String THEME_DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final Integer THEME_PRICE_DATA = 29000;

    private static final Theme THEME1 = new Theme(
            THEME_ID_DATA, THEME_NAME_DATA1, THEME_DESC_DATA, THEME_PRICE_DATA);

    private static final Theme THEME2 = new Theme(
            THEME_ID_DATA, THEME_NAME_DATA2, THEME_DESC_DATA, THEME_PRICE_DATA);

    private final ConnectionManager connectionManager = new ConnectionManager(
            CONNECTION_SETTING,
            CONNECTION_POOL_SETTING);

    @DisplayName("쿼리 테스트")
    @ParameterizedTest
    @MethodSource("getQueryData")
    <T> void query(PreparedStatementCreator psc, ResultSetExtractor<T> rse, T expected) {
        assertThat(connectionManager.query(psc, rse)).isEqualTo(expected);
    }

    private static Stream<Arguments> getQueryData() {
        return Stream.of(
                Arguments.arguments(
                        new ExistReservationIdPreparedStatementCreator(1L),
                        ReservationDAO.existResultSetExtractor, true),
                Arguments.arguments(
                        new ExistReservationIdPreparedStatementCreator(2L),
                        ReservationDAO.existResultSetExtractor, false),
                Arguments.arguments(
                        new ExistReservationPreparedStatementCreator(RESERVATION1),
                        ReservationDAO.existResultSetExtractor, true),
                Arguments.arguments(
                        new ExistReservationPreparedStatementCreator(RESERVATION2),
                        ReservationDAO.existResultSetExtractor, false),
                Arguments.arguments(
                        new ExistReservationThemeIdPreparedStatementCreator(1L),
                        ReservationDAO.existResultSetExtractor, false),
                Arguments.arguments(
                        new ExistReservationThemeIdPreparedStatementCreator(2L),
                        ReservationDAO.existResultSetExtractor, true),
                Arguments.arguments(
                        new ExistThemeIdPreparedStatementCreator(1L),
                        ReservationDAO.existResultSetExtractor, true),
                Arguments.arguments(
                        new ExistThemeIdPreparedStatementCreator(2L),
                        ReservationDAO.existResultSetExtractor, true),
                Arguments.arguments(
                        new ExistThemeIdPreparedStatementCreator(3L),
                        ReservationDAO.existResultSetExtractor, false),
                Arguments.arguments(
                        new ExistThemePreparedStatementCreator(THEME1),
                        ReservationDAO.existResultSetExtractor, true),
                Arguments.arguments(
                        new ExistThemePreparedStatementCreator(THEME2),
                        ReservationDAO.existResultSetExtractor, false)
        );
    }

    @DisplayName("예약 조회 쿼리 테스트")
    @Test
    void queryFindReservation() {
        Reservation reservation = connectionManager.query(
                new FindReservationPreparedStatementCreator(1L),
                ReservationDAO.reservationResultSetExtractor);

        assertThat(reservation.getDate()).isEqualTo(RESERVATION_DATE_DATA1);
        assertThat(reservation.getTime()).isEqualTo(RESERVATION_TIME_DATA);
        assertThat(reservation.getName()).isEqualTo(RESERVATION_NAME_DATA);
        assertThat(reservation.getThemeId()).isEqualTo(RESERVATION_THEME_ID_DATA);
    }

    @DisplayName("테마 조회 쿼리 테스트")
    @Test
    void queryFindTheme() {
        Theme theme = connectionManager.query(
                new FindThemePreparedStatementCreator(1L),
                ThemeDAO.themeResultSetExtractor);

        assertThat(theme.getName()).isEqualTo(THEME_NAME_DATA1);
        assertThat(theme.getDesc()).isEqualTo(THEME_DESC_DATA);
        assertThat(theme.getPrice()).isEqualTo(THEME_PRICE_DATA);
    }

    @DisplayName("테마 목록 조회 쿼리 테스트")
    @Test
    void queryListTheme() {
        List<Theme> themeList = connectionManager.query(
                new ListThemePreparedStatementCreator(),
                ThemeDAO.themeRowMapper);

        assertThat(themeList.size()).isEqualTo(2);
        assertThat(themeList.get(0).getName()).isEqualTo(THEME_NAME_DATA1);
        assertThat(themeList.get(0).getDesc()).isEqualTo(THEME_DESC_DATA);
        assertThat(themeList.get(0).getPrice()).isEqualTo(THEME_PRICE_DATA);
    }

    @DisplayName("업데이트 테스트")
    @ParameterizedTest
    @MethodSource("getUpdateData")
    void update(PreparedStatementCreator psc) {
        assertThatCode(() -> connectionManager.update(psc)).doesNotThrowAnyException();
    }

    private static Stream<Arguments> getUpdateData() {
        return Stream.of(
                Arguments.arguments(
                        new RemoveReservationPreparedStatementCreator(1L)),
                Arguments.arguments(
                        new RemoveThemePreparedStatementCreator(1L))
        );
    }

    @DisplayName("업데이트 키 테스트")
    @ParameterizedTest
    @MethodSource("getUpdateAndGetKeyData")
    void updateAndGetKey(PreparedStatementCreator psc, Long expected) {
        Long id =  connectionManager.updateAndGetKey(psc, "id", Long.class);

        assertThat(id).isEqualTo(expected);
    }

    private static Stream<Arguments> getUpdateAndGetKeyData() {
        return Stream.of(
                Arguments.arguments(
                        new InsertReservationPreparedStatementCreator(RESERVATION2), 2L),
                Arguments.arguments(
                        new InsertThemePreparedStatementCreator(THEME2), 3L)
        );
    }
}
