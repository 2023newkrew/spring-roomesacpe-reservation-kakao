package nextstep.web.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationDAOImpl reservationDAOImpl;

    public ReservationRepositoryImpl(ReservationDAOImpl reservationDAOImpl) {
        this.reservationDAOImpl = reservationDAOImpl;
    }

    @Override
    public Long insertWithKeyHolder(Reservation reservation) {
        return reservationDAOImpl.insertWithKeyHolder(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationDAOImpl.findById(id);
    }

    @Override
    public List<Reservation> findByDateAndTime(LocalDate localDate, LocalTime localTime) {
        return reservationDAOImpl.findByDateAndTime(localDate, localTime);
    }

    @Override
    public List<Reservation> findByTheme(Theme theme) {
        return reservationDAOImpl.findByTheme(theme);
    }

    @Override
    public Integer delete(Long id) {
        return reservationDAOImpl.delete(id);
    }
}
