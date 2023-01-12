package reservation.service;

import org.springframework.stereotype.Service;
import reservation.model.domain.Reservation;
import reservation.model.dto.RequestReservation;
import reservation.util.exception.restAPI.DuplicateException;
import reservation.respository.ReservationJdbcTemplateRepository;
import reservation.util.exception.restAPI.NotFoundException;

import static reservation.util.exception.ErrorMessages.RESERVATION_DUPLICATED;
import static reservation.util.exception.ErrorMessages.RESERVATION_NOT_FOUND;

@Service
public class ReservationService {
    private final ReservationJdbcTemplateRepository reservationJdbcTemplateRepository;

    public ReservationService(ReservationJdbcTemplateRepository reservationJdbcTemplateRepository) {
        this.reservationJdbcTemplateRepository = reservationJdbcTemplateRepository;
    }

    public Long createReservation(RequestReservation requestReservation) {
        if(reservationJdbcTemplateRepository.existByDateTime(requestReservation.getDate(), requestReservation.getTime())){
            throw new DuplicateException(RESERVATION_DUPLICATED);
        }

        return reservationJdbcTemplateRepository.save(changeToReservation(requestReservation));
    }

    public Reservation getReservation(Long id) {
        if(!reservationJdbcTemplateRepository.existById(id)){
            throw new NotFoundException(RESERVATION_NOT_FOUND);
        }
        return reservationJdbcTemplateRepository.findById(id);
    }

    public void deleteReservation(Long id) {
        if(!reservationJdbcTemplateRepository.existById(id)){
            throw new NotFoundException(RESERVATION_NOT_FOUND);
        }
        reservationJdbcTemplateRepository.deleteById(id);
    }

    // 저장되기 이전의 Reservation 객체를 생성 - 레이어 간 DTO 구분
    private Reservation changeToReservation(RequestReservation req) {
        return new Reservation(0L, req.getDate(), req.getTime(), req.getUsername(), req.getThemeId());
    }
}
