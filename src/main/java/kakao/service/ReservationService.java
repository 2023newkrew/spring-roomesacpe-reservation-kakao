package kakao.service;

import domain.ReservationFactory;
import domain.ReservationValidator;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.repository.ReservationJDBCRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationJDBCRepository reservationJDBCRepository;
    private final ReservationFactory reservationFactory = new ReservationFactory();
    private final ReservationValidator reservationValidator;

    public ReservationService(ReservationJDBCRepository reservationJDBCRepository) {
        this.reservationJDBCRepository = reservationJDBCRepository;
        reservationValidator = new ReservationValidator(reservationJDBCRepository);
    }

    public long createReservation(CreateReservationRequest request) {
        reservationValidator.validateForCreate(request);
        return reservationJDBCRepository.save(reservationFactory.createReservation(request));
    }

    public ReservationResponse getReservation(Long id) {
        return new ReservationResponse(reservationJDBCRepository.findById(id));
    }

    public int deleteReservation(Long id) {
        return reservationJDBCRepository.delete(id);
    }
}
