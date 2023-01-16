package kakao.service;

import domain.Reservation;
import domain.ReservationValidator;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.repository.ReservationJDBCRepository;
import kakao.repository.ReservationRepository;
import kakao.repository.ThemeJDBCRepository;
import kakao.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationJDBCRepository reservationRepository, ThemeJDBCRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public long createReservation(CreateReservationRequest request) {
        ReservationValidator validator = new ReservationValidator(reservationRepository);
        validator.validateForCreate(request.date, request.time);

        return reservationRepository.save(Reservation.builder()
                .date(request.date)
                .time(request.time)
                .name(request.name)
                .theme(themeRepository.findById(request.themeId))
                .build());
    }

    public ReservationResponse getReservation(Long id) {
        return new ReservationResponse(reservationRepository.findById(id));
    }

    public int deleteReservation(Long id) {
        return reservationRepository.delete(id);
    }
}
