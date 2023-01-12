package nextstep.reservation.repository;

import lombok.AllArgsConstructor;
import nextstep.reservation.dao.ReservationDAO;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.dto.ReservationDTO;
import nextstep.reservation.mapper.ReservationMapper;
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
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return dao.existsByDateAndTime(Date.valueOf(date), Time.valueOf(time));
    }

    @Override
    public Long insert(Reservation reservation) {
        return dao.insert(ReservationMapper.INSTANCE.toDto(reservation));
    }

    @Override
    public Reservation getById(Long id) {
        ReservationDTO dto = dao.getById(id);

        return ReservationMapper.INSTANCE.fromDto(dto);
    }

    @Override
    public boolean deleteById(Long id) {
        return dao.deleteById(id);
    }
}
