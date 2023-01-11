package reservation.service;

import reservation.model.domain.Theme;
import org.springframework.stereotype.Service;
import reservation.model.domain.Reservation;
import reservation.model.dto.RequestReservation;
import reservation.util.exception.DuplicateException;
import reservation.respository.ReservationJdbcTemplateRepository;
import reservation.util.exception.NotFoundException;

import static reservation.util.ErrorStatus.RESERVATION_DUPLICATED;
import static reservation.util.ErrorStatus.RESERVATION_NOT_FOUND;

@Service
public class ReservationService {
    private final ReservationJdbcTemplateRepository reservationJdbcTemplateRepository;
    private final Theme theme;

    public ReservationService(ReservationJdbcTemplateRepository reservationJdbcTemplateRepository) {
        this.reservationJdbcTemplateRepository = reservationJdbcTemplateRepository;
        this.theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    }

    public Long createReservation(RequestReservation requestReservation) {
        if(reservationJdbcTemplateRepository.existByDateTime(requestReservation.getDate(), requestReservation.getTime())){
            throw new DuplicateException(RESERVATION_DUPLICATED);
        }

        return reservationJdbcTemplateRepository.save(makeReservationBeforeStore(requestReservation, theme));
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
    private Reservation makeReservationBeforeStore(RequestReservation req, Theme theme) {
        return new Reservation(0L, req.getDate(), req.getTime(), req.getName(), theme);
    }
}
