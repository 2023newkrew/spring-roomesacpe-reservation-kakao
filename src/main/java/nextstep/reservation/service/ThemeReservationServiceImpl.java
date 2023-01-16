package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.exception.ConstraintViolationException;
import nextstep.exception.EntityNotFoundException;
import nextstep.reservation.dao.ThemeReservationDao;
import nextstep.reservation.dto.ReservationDetail;
import nextstep.reservation.dto.ReservationDto;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.exception.ThemeReservationErrorCode;
import nextstep.theme.dao.ThemeDao;
import nextstep.theme.entity.Theme;
import nextstep.theme.exception.ThemeErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThemeReservationServiceImpl implements ThemeReservationService {

    private final ThemeReservationDao themeReservationDao;
    private final ThemeDao themeDao;

    @Override
    @Transactional(readOnly = false)
    public Long reserve(ReservationDto reservationDto){
        if(isExistSameDatetimeReservation(reservationDto)){
            throw new ConstraintViolationException(ThemeReservationErrorCode.CANNOT_RESERVE_FOR_SAME_DATETIME);
        }

        Reservation reservation = reservationDto.toEntity();
        themeReservationDao.insert(reservation);
        return reservation.getId();
    }

    private boolean isExistSameDatetimeReservation(ReservationDto reservationDto){
        return themeReservationDao.findByDatetime(reservationDto.getDate(), reservationDto.getTime())
                .isPresent();
    }

    @Override
    @Transactional(readOnly = false)
    public void cancelById(Long id){
        int deleteCount = themeReservationDao.delete(id);
        if(deleteCount == 0){
            throw new EntityNotFoundException(ThemeReservationErrorCode.RESERVATION_NOT_FOUND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDetail findById(Long id){
        Reservation reservation = themeReservationDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ThemeReservationErrorCode.RESERVATION_NOT_FOUND));

        Theme theme = themeDao.findById(reservation.getThemeId())
                .orElseThrow(() -> new EntityNotFoundException(ThemeErrorCode.THEME_NOT_FOUND));

        return new ReservationDetail(reservation, theme);
    }
}
