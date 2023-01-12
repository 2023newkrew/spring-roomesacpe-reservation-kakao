package nextstep.main.java.nextstep.mvc.service;

import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import nextstep.main.java.nextstep.mvc.repository.reservation.ReservationRepository;
import nextstep.main.java.nextstep.mvc.service.reservation.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("[예약 생성] 성공 케이스 테스트")
    void findOneByIdTest() {
        ReservationCreateRequest request = getCreateRequest();

        given(reservationRepository.save(any())).willReturn(1L);

        reservationService.save(request);
    }

    private ReservationCreateRequest getCreateRequest() {
        return ReservationCreateRequest.of(
                LocalDate.of(2023, 1, 19),
                LocalTime.of(3, 30),
                "test",
                "test");
    }
//
//    @Test
//    @DisplayName("예약 단건 삭제 테스트")
//    void deleteOneByIdTest() {
//        assertThatCode(() -> service.findOneById(1L)).doesNotThrowAnyException();
//
//        service.deleteOneById(1L);
//        assertThatThrownBy(() -> service.findOneById(1L)).isInstanceOf(NoSuchReservationException.class);
//    }
//
//    @Test
//    @DisplayName("예약 중복 등록 테스트")
//    void createDuplicateTest(){
//        ReservationCreateRequest requestDto = new ReservationCreateRequest(LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name");
//            assertThatThrownBy(() -> service.save(requestDto)).isInstanceOf(DuplicateReservationException.class);
//    }

}
