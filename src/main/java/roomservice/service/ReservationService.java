package roomservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomservice.domain.Reservation;
import roomservice.repository.ReservationDao;

/**
 * ReservationService processes interior logic of reservation system.
 * This class can request DAO to get information from database.
 */
@Service
public class ReservationService {
    ReservationDao reservationDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
    }

    /**
     * Request DAO to insert reservation.
     * @param reservation reservation to be added.
     * @return id if successfully created.
     */
    public Long createReservation(Reservation reservation){
        return reservationDao.insertReservation(reservation);
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
