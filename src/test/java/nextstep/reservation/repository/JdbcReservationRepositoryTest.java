package nextstep.reservation.repository;

import nextstep.etc.util.StatementCreator;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.Theme;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.mapper.ReservationMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

@SqlGroup(
        {
                @Sql("classpath:/dropTable.sql"),
                @Sql("classpath:/schema.sql")
        })
@JdbcTest
class JdbcReservationRepositoryTest {

    final JdbcTemplate jdbcTemplate;

    final ReservationRepository repository;
    final ReservationMapper mapper;

    @Autowired
    public JdbcReservationRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = new JdbcReservationRepository(jdbcTemplate);
        this.mapper = Mappers.getMapper(ReservationMapper.class);
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class existsByDateAndTime {

        @BeforeEach
        void setUp() {
            List<ReservationRequest> requests = List.of(
                    new ReservationRequest("2022-08-12", "13:00", "류성현"),
                    new ReservationRequest("2022-08-11", "14:00", "류성현"),
                    new ReservationRequest("2022-08-11", "13:00", "pluto")
            );
            insertTestReservations(requests);
        }

        @DisplayName("date와 time이 같은 예약이 존재하는지 확인")
        @ParameterizedTest
        @MethodSource
        void should_returnExists_when_givenRequest(ReservationRequest request, boolean exists) {
            boolean actual = repository.existsByDateAndTime(fromRequest(request));

            Assertions.assertThat(actual)
                    .isEqualTo(exists);
        }

        List<Arguments> should_returnExists_when_givenRequest() {
            return List.of(
                    Arguments.of(new ReservationRequest("2022-08-11", "14:00", "류성현"), true),
                    Arguments.of(new ReservationRequest("2022-08-12", "13:00", "pluto"), true),
                    Arguments.of(new ReservationRequest("2022-08-11", "16:00", "류성현"), false),
                    Arguments.of(new ReservationRequest("2022-08-13", "13:00", "pluto"), false)
            );
        }

        @DisplayName("삽입 후 중복여부 확인")
        @ParameterizedTest
        @MethodSource
        void should_returnFalse_when_afterInsert(ReservationRequest request) {
            Reservation reservation = fromRequest(request);

            boolean before = repository.existsByDateAndTime(reservation);
            insertTestReservations(List.of(request));
            boolean after = repository.existsByDateAndTime(reservation);

            Assertions.assertThat(before)
                    .isFalse();
            Assertions.assertThat(after)
                    .isTrue();
        }

        List<Arguments> should_returnFalse_when_afterInsert() {
            return List.of(
                    Arguments.of(new ReservationRequest("2023-08-11", "14:00", "류성현"), 1L),
                    Arguments.of(new ReservationRequest("2023-08-12", "13:00", "pluto"), 2L),
                    Arguments.of(new ReservationRequest("2023-08-11", "16:00", "류성현"), 3L),
                    Arguments.of(new ReservationRequest("2023-08-13", "13:00", "pluto"), 4L)
            );
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class insert {

        @DisplayName("호출 횟수만큼 ID가 증가하는지 확인")
        @Test
        void should_increaseId_when_insertTwice() {
            ReservationRequest request = new ReservationRequest("2022-08-11", "14:00", "류성현");
            Reservation reservation = fromRequest(request);

            Long id1 = repository.insert(reservation)
                    .getId();
            Long id2 = repository.insert(reservation)
                    .getId();

            Assertions.assertThat(id1 + 1L)
                    .isEqualTo(id2);
        }

        @DisplayName("저장된 예약이 요청한 값이 맞는지 확인")
        @ParameterizedTest
        @MethodSource
        void should_returnReservation_when_givenRequest(ReservationRequest request) {
            Reservation reservation = fromRequest(request);

            Reservation actual = repository.insert(reservation);

            Assertions.assertThat(actual)
                    .extracting(
                            Reservation::getDate,
                            Reservation::getTime,
                            Reservation::getName
                    )
                    .contains(
                            reservation.getDate(),
                            reservation.getTime(),
                            reservation.getName()
                    );
        }

        List<Arguments> should_returnReservation_when_givenRequest() {
            return List.of(
                    Arguments.of(new ReservationRequest("2022-08-11", "14:00", "류성현")),
                    Arguments.of(new ReservationRequest("2022-08-12", "13:00", "pluto")),
                    Arguments.of(new ReservationRequest("2022-08-11", "16:00", "류성현")),
                    Arguments.of(new ReservationRequest("2022-08-13", "13:00", "pluto"))
            );
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class getById {

        @BeforeEach
        void setUp() {
            List<ReservationRequest> requests = List.of(
                    new ReservationRequest("2022-08-12", "13:00", "류성현"),
                    new ReservationRequest("2022-08-11", "14:00", "류성현"),
                    new ReservationRequest("2022-08-11", "13:00", "pluto")
            );
            insertTestReservations(requests);
        }

        @DisplayName("ID와 일치하는 예약 확인")
        @ParameterizedTest
        @MethodSource
        void should_returnReservation_when_givenId(Long id, ReservationRequest request) {
            Reservation reservation = fromRequest(request);

            Reservation actual = repository.getById(id);

            Assertions.assertThat(actual)
                    .extracting(
                            Reservation::getId,
                            Reservation::getDate,
                            Reservation::getTime,
                            Reservation::getName
                    )
                    .contains(
                            id,
                            reservation.getDate(),
                            reservation.getTime(),
                            reservation.getName()
                    );
        }


        List<Arguments> should_returnReservation_when_givenId() {
            return List.of(
                    Arguments.of(1L, new ReservationRequest("2022-08-12", "13:00", "류성현")),
                    Arguments.of(2L, new ReservationRequest("2022-08-11", "14:00", "류성현")),
                    Arguments.of(3L, new ReservationRequest("2022-08-11", "13:00", "pluto"))
            );
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class deleteById {

        @BeforeEach
        void setUp() {
            List<ReservationRequest> requests = List.of(
                    new ReservationRequest("2022-08-12", "13:00", "류성현"),
                    new ReservationRequest("2022-08-11", "14:00", "류성현"),
                    new ReservationRequest("2022-08-11", "13:00", "pluto")
            );
            insertTestReservations(requests);
        }

        @DisplayName("예약이 존재하면 제거하고 결과 반환")
        @ParameterizedTest
        @CsvSource({"1, true", "2, true", "4, false"})
        void should_returnIsDeleted_when_givenId(Long id, boolean isDeleted) {
            boolean actual = repository.deleteById(id);

            Assertions.assertThat(actual)
                    .isEqualTo(isDeleted);
        }

        @DisplayName("두번 제거할 경우 실패")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void should_returnFalse_when_deleteTwice(Long id) {
            boolean before = repository.deleteById(id);
            boolean after = repository.deleteById(id);

            Assertions.assertThat(before)
                    .isTrue();
            Assertions.assertThat(after)
                    .isFalse();
        }
    }

    private void insertTestReservations(List<ReservationRequest> requests) {
        requests.forEach(req -> {
            Reservation reservation = fromRequest(req);
            jdbcTemplate.update(conn -> StatementCreator.createInsertStatement(conn, reservation));
        });
    }

    private Reservation fromRequest(ReservationRequest req) {
        Theme theme = new Theme("", "", 0);
        return mapper.fromRequest(req, theme);
    }
}

