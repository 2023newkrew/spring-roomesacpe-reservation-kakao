package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Reservation;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
@Primary
public class JdbcReservationRepository implements ReservationRepository{
    @Override
    public void save(Reservation reservation) {

    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteOne(Long id) {

    }

    @Override
    public Optional<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        return Optional.empty();
    }
}
