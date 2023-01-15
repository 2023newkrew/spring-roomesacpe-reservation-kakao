package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dao.ThemeReservationDao;
import nextstep.reservation.dto.ReservationDetail;
import nextstep.reservation.dto.ReservationDto;
import nextstep.reservation.entity.Reservation;
import nextstep.theme.dao.ThemeDao;
import nextstep.theme.entity.Theme;
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
        int deleteCount = themeReservationDao.delete(id);
        if(deleteCount == 0){
            throw new SQLException();
        }
    }

    @Override
    public ReservationDetail findById(Long id) throws SQLException{
        Reservation reservation = themeReservationDao.findById(id)
                .orElseThrow(() -> new RuntimeException());

        Theme theme = themeDao.findById(reservation.getThemeId());
        if(theme == null){
            return null;
        }
        return new ReservationDetail(reservation, theme);
    }
}
