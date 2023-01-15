package nextstep.step3.service;

import lombok.RequiredArgsConstructor;
import nextstep.step3.dao.ThemeDao;
import nextstep.step3.dao.ThemeReservationDao;
import nextstep.step3.dto.ReservationDetail;
import nextstep.step3.dto.ReservationDto;
import nextstep.step3.entity.Reservation;
import nextstep.step3.entity.Theme;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ThemeReservationServiceImpl implements ThemeReservationService {

    private final ThemeReservationDao themeReservationDao;
    private final ThemeDao themeDao;

    private static final Long DEFAULT_THEME_ID = 1L;

    @Override
    public Long reserve(ReservationDto reservationDto) throws SQLException {
        reservationDto.setThemeId(DEFAULT_THEME_ID);
        Reservation reservation = ReservationDto.from(reservationDto);
        themeReservationDao.insert(reservation);
        return reservation.getId();
    }

    @Override
    public void cancelById(Long id) throws SQLException{
        int deleteCount = themeReservationDao.deleteReservation(id);
        if(deleteCount == 0){
            throw new SQLException();
        }
    }

    @Override
    public ReservationDetail findById(Long id) throws SQLException{
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
