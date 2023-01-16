package kakao.service;

import domain.Reservation;
import domain.ReservationValidator;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.repository.ReservationJDBCRepository;
import kakao.repository.ThemeMemRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationJDBCRepository reservationJDBCRepository;

    public ReservationService(ReservationJDBCRepository reservationJDBCRepository) {
        this.reservationJDBCRepository = reservationJDBCRepository;
    }

    public long createReservation(CreateReservationRequest request) {
        ReservationValidator validator = new ReservationValidator(reservationJDBCRepository);
        validator.validateForCreate(request.date, request.time);

        return reservationJDBCRepository.save(Reservation.builder()
                .date(request.date)
                .time(request.time)
                .name(request.name)
                .theme(ThemeMemRepository.theme)
                .build());
    }

    public ReservationResponse getReservation(Long id) {
        return new ReservationResponse(reservationJDBCRepository.findById(id));
    }

    public int deleteReservation(Long id) {
        return reservationJDBCRepository.delete(id);
    }
}
