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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation createReservation(CreateReservationRequest request) {
        boolean isDuplicate = reservationRepository.findByDateAndTime(request.date, request.time).size() > 0;
        if (isDuplicate) {
            throw new DuplicatedReservationException(ErrorCode.DUPLICATE_RESERVATION);
        }
        return reservationRepository.save(Reservation.builder()
                .date(request.date)
                .time(request.time)
                .name(request.name)
                .theme(ThemeRepository.theme)
                .build()
        );
    }

    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (Objects.isNull(reservation)) {
            throw new RecordNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        return new ReservationResponse(reservation);
    }

    public int deleteReservation(Long id) {
        return reservationRepository.delete(id);
    }
}
