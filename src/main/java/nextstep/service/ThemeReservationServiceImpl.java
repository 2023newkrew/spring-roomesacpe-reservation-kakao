package nextstep.service;

import lombok.RequiredArgsConstructor;
import nextstep.dao.ThemeDao;
import nextstep.dao.ThemeReservationDao;
import nextstep.dto.ReservationDetail;
import nextstep.dto.ReservationDto;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeReservationServiceImpl implements ThemeReservationService {

    private final ThemeReservationDao themeReservationDao;
    private final ThemeDao themeDao;

    private static final Long DEFAULT_THEME_ID = 1L;

    @Override
    public Long reserve(ReservationDto reservationDto) {
        reservationDto.setThemeId(DEFAULT_THEME_ID);
        return themeReservationDao.createReservation(ReservationDto.from(reservationDto));
    }

    @Override
    public void cancelById(Long id) {
        themeReservationDao.deleteReservation(id);
    }

    @Override
    public ReservationDetail findById(Long id) {
        Reservation reservation = themeReservationDao.findById(id);
        if (reservation == null) {
            return null;
        }
        Theme theme = themeDao.findById(reservation.getThemeId());
        if(theme == null){
            return null;
        }
        return new ReservationDetail(reservation, theme);
    }
}
