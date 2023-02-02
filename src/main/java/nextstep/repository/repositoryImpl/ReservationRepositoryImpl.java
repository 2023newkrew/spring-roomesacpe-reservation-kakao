package nextstep.repository.repositoryImpl;

import lombok.AllArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.dao.ReservationDAO;
import nextstep.repository.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationDAO dao;

    @Override
    public Boolean existsByDateTime(LocalDate date, LocalTime time) {
        if(dao.existsByDateTime(Date.valueOf(date), Time.valueOf(time))) {
            return true;
        }

        return false;
    }

    @Override
    public Long insert(Reservation reservation){
        return dao.insert(reservation);
    }

    @Override
    public Reservation getById(Long id){
        return dao.getById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        return dao.deleteById(id);
    }
}
