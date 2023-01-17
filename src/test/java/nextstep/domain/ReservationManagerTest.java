package nextstep.domain;

import nextstep.domain.repository.ReservationRepository;
import nextstep.domain.repository.SimpleReservationRepository;
import nextstep.domain.repository.SimpleThemeRepository;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ReservationManagerTest {
    private ReservationRepository reservationRepository;
    private ThemeRepository themeRepository;
    private ReservationManager reservationManager;
    private Long themeId;


    @BeforeEach
    void setUp() {
        reservationRepository = new SimpleReservationRepository();
        themeRepository = new SimpleThemeRepository();
        reservationManager = new ReservationManager(reservationRepository, themeRepository);
        themeId = themeRepository.save(new Theme("테마1", "안녕테마", 100000));
    }

    @DisplayName("예약을 생성한다")
    @Test
    void createReservation() {
        // given
        CreateReservationRequest createReservationRequest
                = new CreateReservationRequest("2022-02-20", "13:00", "davi", themeId);

        // when
        Long id = reservationManager.createReservation(createReservationRequest);

        // then
        assertThat(id).isEqualTo(1L);
    }

    @DisplayName("id로 예약을 조회한다")
    @Test
    void findReservationById() {
        // given
        CreateReservationRequest createReservationRequest
                = new CreateReservationRequest("2022-02-20", "13:00", "davi", themeId);
        Long id = reservationManager.createReservation(createReservationRequest);

        // when
        FindReservationResponse reservationResponse =  reservationManager.findReservationById(id);

        // then
        assertThat(reservationResponse).isEqualTo(new FindReservationResponse(
                id,
                "2022-02-20",
                "13:00",
                "davi",
                themeId,
                "테마1",
                "안녕테마",
                100000
        ));
    }

    @DisplayName("id로 예약을 삭제한다")
    @Test
    void deleteReservationById() {
        // given
        CreateReservationRequest createReservationRequest
                = new CreateReservationRequest("2022-02-20", "13:00", "davi", themeId);
        Long id = reservationManager.createReservation(createReservationRequest);

        // when
        boolean deleted =  reservationManager.deleteReservationById(id);

        // then
        assertThat(deleted).isTrue();
    }
}