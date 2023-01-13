package roomescape.reservation.repository;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.reservation.repository.dao.ReservationDao;
import roomescape.reservation.repository.dao.ReservationDaoImpl;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.reservationDao = new ReservationDaoImpl(jdbcTemplate);
    }

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

    @Override
    public boolean isReservationDuplicated(Reservation reservation) {
        return reservationDao.isReservationDuplicated(reservation);
    }
}
