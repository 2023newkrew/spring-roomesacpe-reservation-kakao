package roomservice.repository;

import roomservice.domain.entity.Reservation;

/**
 * ReservationDao lists what to do with DB.
 */
public interface ReservationDao {
    /**
     * Add a reservation to database.
     *
     * @param reservation reservation to be added.
     * @return id resulted by Database.
     * @throws {@code DuplicatedReservationException} if same reservation exists.
     */
    long insertReservation(Reservation reservation);

    /**
     * Find a reservation from database.
     *
     * @param id which you want to find.
     * @return reservation if successfully found.
     * @throws {@code NonExistentReservationException} if reservation with {id} does not exist.
     */
    Reservation selectReservation(long id);

    /**
     * Delete a reservation from database.
     *
     * @param id which you want to delete.
     * @return reservation if successfully deleted.
     * @throws {@code NonExistentReservationException} if reservation with {id} does not exist.
     */
    void deleteReservation(long id);
}
