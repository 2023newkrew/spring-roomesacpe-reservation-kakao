package nextstep.repository;

import lombok.AllArgsConstructor;
import nextstep.Reservation;
import nextstep.dao.ReservationDAO;
import nextstep.dto.ReservationDTO;
import nextstep.mapper.ReservationMapper;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class ReservationRepositoryImpl implements ReservationRepository{

    private final ReservationDAO dao;

    @Override
    public Long insertIfNotExistsDateTime(Reservation reservation){
        ReservationDTO dto = ReservationMapper.INSTANCE.toDto(reservation);

        return dao.insertIfNotExistsDateTime(dto);
    }

    @Override
    public Reservation getById(Long id){
        ReservationDTO dto = dao.getById(id);
        
        return ReservationMapper.INSTANCE.fromDto(dto);
    }

    @Override
    public void deleteById(Long id){
        dao.deleteById(id);
    }
}
