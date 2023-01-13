package nextstep.reservation.service;

import nextstep.reservation.dto.ReservationDTO;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.repository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

class ReservationServiceTest {

    ReservationService service;

    @BeforeEach
    void init() {
        ReservationRepository repository = new FakeReservationRepository();
        service = new ReservationServiceImpl(repository);
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class create {

        @DisplayName("생성 성공")
        @ParameterizedTest
        @MethodSource
        void should_idIs1_when_created(ReservationRequest request) {
            ReservationDTO reservation = service.create(request);

            Assertions.assertThat(reservation.getId())
                    .isOne();
        }

        List<Arguments> should_idIs1_when_created() {
            return List.of(
                    Arguments.of(new ReservationRequest("2022-08-12", "13:00", "류성현")),
                    Arguments.of(new ReservationRequest("2022-08-11", "14:00", "류성현")),
                    Arguments.of(new ReservationRequest("2022-08-11", "13:00", "pluto"))
            );
        }

        @DisplayName("예약 시각이 같은 경우 예외 발생")
        @ParameterizedTest
        @MethodSource
        void should_throwException_when_givenSameDateAndTime(ReservationRequest request) {
            service.create(request);

            Assertions.assertThatThrownBy(() -> service.create(request))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("해당 시각에는 이미 예약이 존재합니다.");
        }

        List<Arguments> should_throwException_when_givenSameDateAndTime() {
            return List.of(
                    Arguments.of(new ReservationRequest("2022-08-12", "13:00", "류성현")),
                    Arguments.of(new ReservationRequest("2022-08-11", "14:00", "류성현")),
                    Arguments.of(new ReservationRequest("2022-08-11", "13:00", "pluto"))
            );
        }

        @DisplayName("예약 시각이 다른 경우 생성 성공")
        @ParameterizedTest
        @MethodSource
        void should_idIs2_when_givenDiffDateOrTime(ReservationRequest anotherRequest) {
            ReservationRequest request = new ReservationRequest("2022-08-11", "13:00", "류성현");
            service.create(request);

            ReservationDTO reservation = service.create(anotherRequest);

            Assertions.assertThat(reservation.getId())
                    .isEqualTo(2L);
        }

        List<Arguments> should_idIs2_when_givenDiffDateOrTime() {
            return List.of(
                    Arguments.of(new ReservationRequest("2022-08-12", "13:00", "류성현")),
                    Arguments.of(new ReservationRequest("2022-08-11", "14:00", "류성현"))
            );
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class getById {

        @DisplayName("ID가 다른 경우 null 반환")
        @ParameterizedTest
        @ValueSource(longs = {0L, 2L, 3L})
        void should_returnNull_when_notFondId(Long id) {
            ReservationRequest request = new ReservationRequest("2022-08-11", "13:00", "류성현");
            service.create(request);

            ReservationDTO reservationDTO = service.getById(id);

            Assertions.assertThat(reservationDTO)
                    .isNull();
        }

        @DisplayName("ID가 같은 예약을 반환")
        @ParameterizedTest
        @MethodSource
        void should_nameIs_when_givenId(Long id, String reserverName) {
            List<ReservationRequest> requests = List.of(
                    new ReservationRequest("2022-08-12", "13:00", "류성현"),
                    new ReservationRequest("2022-08-11", "14:00", "류성현"),
                    new ReservationRequest("2022-08-11", "13:00", "pluto")
            );
            requests.forEach(service::create);

            ReservationDTO reservationDTO = service.getById(id);

            Assertions.assertThat(reservationDTO.getName())
                    .isEqualTo(reserverName);
        }

        List<Arguments> should_nameIs_when_givenId() {
            return List.of(
                    Arguments.of(1L, "류성현"),
                    Arguments.of(2L, "류성현"),
                    Arguments.of(3L, "pluto")
            );
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class deleteById {

        @DisplayName("예약을 취소하고 결과 반환")
        @ParameterizedTest
        @CsvSource(value = {"0, false", "1, true", "2, false"})
        void should_returnResult_when_givenId(Long id, boolean result) {
            ReservationRequest request = getTestRequest();
            service.create(request);

            boolean actual = service.deleteById(id);

            Assertions.assertThat(actual)
                    .isEqualTo(result);
        }

        @DisplayName("이미 취소한 예약을 조회할 경우 null 반환")
        @Test
        void should_returnNull_when_getDeleted() {
            ReservationRequest request = getTestRequest();
            ReservationDTO reservation = service.create(request);
            service.deleteById(reservation.getId());

            ReservationDTO actual = service.getById(reservation.getId());

            Assertions.assertThat(actual)
                    .isNull();
        }

        @DisplayName("이미 취소한 예약을 취소할 경우 false 반환")
        @Test
        void should_returnResult_when_givenId() {
            ReservationRequest request = getTestRequest();
            ReservationDTO reservation = service.create(request);
            service.deleteById(reservation.getId());

            boolean actual = service.deleteById(reservation.getId());

            Assertions.assertThat(actual)
                    .isFalse();
        }
    }

    private static ReservationRequest getTestRequest() {
        return new ReservationRequest("2022-08-12", "13:00", "류성현");
    }
}