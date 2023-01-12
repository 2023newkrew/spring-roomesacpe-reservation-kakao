package roomservice.repository;

import roomservice.domain.entity.Reservation;

/**
 * ReservationDao lists what to do with DB.
 */
public interface ReservationDao {
    /**
     * Add a reservation to database.
     * @throws {@code DuplicatedReservationException} if same reservation exists.
     * @param reservation reservation to be added.
     * @return id resulted by Database.
     */
    long insertReservation(Reservation reservation);

    /**
     * Find a reservation from database.
     * @throws {@code NonExistentReservationException} if reservation with {id} does not exist.
     * @param id which you want to find.
     * @return reservation if successfully found.
     */
    Reservation selectReservation(long id);

    /**
     * Delete a reservation from database.
     * @throws {@code NonExistentReservationException} if reservation with {id} does not exist.
     * @param id which you want to delete.
     * @return reservation if successfully deleted.
     */
    void deleteReservation(long id);
}
