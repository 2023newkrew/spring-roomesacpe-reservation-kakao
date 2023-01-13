package roomescape.reservation.repository;

import java.util.Optional;
import roomescape.entity.Reservation;
import roomescape.reservation.repository.dao.ReservationDao;
import roomescape.reservation.repository.dao.ReservationDaoWithoutTemplateImpl;

public class ReservationRepositoryWithoutTemplateImpl implements ReservationRepository {
    private final ReservationDao reservationDao = new ReservationDaoWithoutTemplateImpl();

    @Override
    public Long save(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationDao.findById(id);
    }

    @Override
    public int delete(Long id) {
        return reservationDao.delete(id);
    }

    public boolean isReservationDuplicated(Reservation reservation) {
        return reservationDao.isReservationDuplicated(reservation);
    }
}
