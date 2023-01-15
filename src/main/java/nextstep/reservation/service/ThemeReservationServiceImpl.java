package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.exception.EntityNotFoundException;
import nextstep.reservation.dao.ThemeReservationDao;
import nextstep.reservation.dto.ReservationDetail;
import nextstep.reservation.dto.ReservationDto;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.exception.ThemeExceptionErrorCode;
import nextstep.theme.dao.ThemeDao;
import nextstep.theme.entity.Theme;
import nextstep.theme.exception.ThemeErrorCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemeReservationServiceImpl implements ThemeReservationService {

    private final ThemeReservationDao themeReservationDao;
    private final ThemeDao themeDao;

    private static final Long DEFAULT_THEME_ID = 1L;

    @Override
    public Long reserve(ReservationDto reservationDto){
        reservationDto.setThemeId(DEFAULT_THEME_ID);
        Reservation reservation = ReservationDto.from(reservationDto);
        themeReservationDao.insert(reservation);
        return reservation.getId();
    }

    @Override
    public void cancelById(Long id){
        int deleteCount = themeReservationDao.delete(id);
        if(deleteCount == 0){
            throw new EntityNotFoundException(ThemeExceptionErrorCode.RESERVATION_NOT_FOUND);
        }
    }

    @Override
    public Optional<ReservationDetail> findById(Long id){
        Reservation reservation = themeReservationDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ThemeExceptionErrorCode.RESERVATION_NOT_FOUND));

        Theme theme = themeDao.findById(reservation.getThemeId())
                .orElseThrow(() -> new EntityNotFoundException(ThemeErrorCode.THEME_NOT_FOUND));

        return Optional.of(new ReservationDetail(reservation, theme));
    }
}
