package nextstep.step1.service;

import lombok.RequiredArgsConstructor;
import nextstep.step1.dao.ThemeDao;
import nextstep.step1.dao.ThemeReservationDao;
import nextstep.step1.dto.ReservationDetail;
import nextstep.step1.dto.ReservationDto;
import nextstep.step1.entity.Reservation;
import nextstep.step1.entity.Theme;
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
        return themeReservationDao.createReservation(reservationDto.toEntity());
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
