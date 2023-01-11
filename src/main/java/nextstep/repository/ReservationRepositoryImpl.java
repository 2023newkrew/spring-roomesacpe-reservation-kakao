package nextstep.repository;

import lombok.AllArgsConstructor;
import nextstep.Reservation;
import nextstep.dao.ReservationDAO;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class ReservationRepositoryImpl implements ReservationRepository{

    private final ReservationDAO dao;

    @Override
    public Long insertIfNotExistsDateTime(Reservation reservation){
        return null;
    }

    @Override
    public Reservation getById(Long id){
        return null;
    }

    @Override
    public void deleteById(Long id){

    }
}
