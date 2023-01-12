package reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.model.domain.Reservation;
import reservation.model.dto.RequestReservation;
import reservation.respository.ThemeJdbcTemplateRepository;
import reservation.util.exception.restAPI.DuplicateException;
import reservation.respository.ReservationJdbcTemplateRepository;
import reservation.util.exception.restAPI.NotFoundException;

import static reservation.util.exception.ErrorMessages.*;

@Service
public class ReservationService {
    private final ReservationJdbcTemplateRepository reservationJdbcTemplateRepository;
    private final ThemeJdbcTemplateRepository themeJdbcTemplateRepository;

    @Autowired
    public ReservationService(ReservationJdbcTemplateRepository reservationJdbcTemplateRepository, ThemeJdbcTemplateRepository themeJdbcTemplateRepository) {
        this.reservationJdbcTemplateRepository = reservationJdbcTemplateRepository;
        this.themeJdbcTemplateRepository = themeJdbcTemplateRepository;
    }


    public Long createReservation(RequestReservation requestReservation) {
        // 예약하려고 하는 테마가 존재하는지 확인
        if(!themeJdbcTemplateRepository.checkExistById(requestReservation.getThemeId())){
            throw new NotFoundException(THEME_NOT_FOUND);
        }

        // 같은 시간에 같은 테마로 예약된 예약이 있는지 확인
        if(reservationJdbcTemplateRepository.existByDateTimeTheme(
                requestReservation.getDate(), requestReservation.getTime(), requestReservation.getThemeId())){
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
        return new Reservation(0L, req.getDate(), req.getTime(), req.getName(), req.getThemeId());
    }
}
