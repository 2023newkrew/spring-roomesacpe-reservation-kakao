package roomservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomservice.domain.TimeTable;
import roomservice.domain.dto.ReservationDto;
import roomservice.domain.entity.Reservation;
import roomservice.domain.entity.Theme;
import roomservice.exceptions.exception.InvalidReservationTimeException;
import roomservice.exceptions.exception.InvalidThemeException;
import roomservice.repository.ReservationDao;
import roomservice.repository.ThemeDao;

import java.time.LocalTime;
import java.util.Arrays;

/**
 * ReservationService processes interior logic of reservation system.
 * This class can request DAO to get information from database.
 */
@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ThemeService themeService;
    private final ThemeDao themeDao;

    /**
     * Request DAO to insert reservation.
     * @throws InvalidReservationTimeException when given time doesn't exist in {@link roomservice.domain.TimeTable}
     * @throws InvalidThemeException when given theme id is not exist.
     * @param reservationDto information of reservation to be added.
     * @return id if successfully created.
     */
    public Long createReservation(ReservationDto reservationDto){
        validateTime(reservationDto.getTime());
        Theme theme = themeDao.selectThemeById(reservationDto.getThemeId());
        validateTheme(theme);
        Reservation reservation = new Reservation(
                null, reservationDto.getDate(), reservationDto.getTime(),
                reservationDto.getName(), theme);
        return reservationDao.insertReservation(reservation);
    }
    private void validateTheme(Theme theme){
        if (theme == null){
            throw new InvalidThemeException();
        }
        if (theme.getName().isBlank()){
            throw new InvalidThemeException();
        }
    }
    private void validateTime(LocalTime reservationTime){
        long count = Arrays.stream(TimeTable.values())
                .filter(t -> t.getTime().equals(reservationTime))
                .count();
        if (count == 0){
            throw new InvalidReservationTimeException();
        }
    }

    /**
     * find a reservation which have specific id through DAO.
     * @param id which you want to find.
     * @return reservation if successfully found.
     */
    public Reservation findReservation(Long id){
        return reservationDao.selectReservation(id);
    }

    /**
     * delete a reservation which have specific id through DAO.
     * @param id which you want to delete.
     */
    public void deleteReservation(Long id){
        reservationDao.deleteReservation(id);
    }
}
