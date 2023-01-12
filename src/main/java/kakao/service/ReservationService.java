package kakao.service;

import domain.ReservationFactory;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.repository.ReservationJDBCRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationJDBCRepository reservationJDBCRepository;
    private final ReservationFactory reservationFactory;

    public ReservationService(ReservationJDBCRepository reservationJDBCRepository) {
        this.reservationJDBCRepository = reservationJDBCRepository;
        this.reservationFactory = new ReservationFactory(reservationJDBCRepository);
    }

    public long createReservation(CreateReservationRequest request) {
        return reservationJDBCRepository.save(reservationFactory.createReservation(request));
    }

    public ReservationResponse getReservation(Long id) {
        return new ReservationResponse(reservationJDBCRepository.findById(id));
    }

    public int deleteReservation(Long id) {
        return reservationJDBCRepository.delete(id);
    }
}
