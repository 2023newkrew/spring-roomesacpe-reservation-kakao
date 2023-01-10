package kakao.service;

import kakao.domain.Reservation;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.error.ErrorCode;
import kakao.error.exception.DuplicatedReservationException;
import kakao.error.exception.RecordNotFoundException;
import kakao.repository.ReservationJDBCRepository;
import kakao.repository.ReservationRepository;
import kakao.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(
            ReservationRepository reservationRepository
    ) {
        this.reservationRepository = reservationRepository;
    }

    public long createReservation(CreateReservationRequest request) {
        boolean isDuplicate = reservationRepository.findByDateAndTime(request.date, request.time).size() > 0;
        if (isDuplicate) {
            throw new DuplicatedReservationException(ErrorCode.DUPLICATE_RESERVATION);
        }
        Reservation reservation = new Reservation(request.date, request.time, request.name, ThemeRepository.theme);
        return reservationRepository.save(reservation);
    }

    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (Objects.isNull(reservation)) {
            throw new RecordNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        return new ReservationResponse(reservation);
    }

    public void deleteReservation(Long id) {
        int deletedCount = reservationRepository.delete(id);
        if (deletedCount == 0) {
            throw new RecordNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }
}
