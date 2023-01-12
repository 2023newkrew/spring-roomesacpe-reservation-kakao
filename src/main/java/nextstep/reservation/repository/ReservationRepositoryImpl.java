package nextstep.reservation.repository;

import lombok.AllArgsConstructor;
import nextstep.reservation.dao.ReservationDAO;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.dto.ReservationDTO;
import nextstep.reservation.mapper.ReservationMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDAO dao;

    @Override
    public Long insertIfNotExistsDateTime(Reservation reservation) {
        Date date = Date.valueOf(reservation.getDate());
        Time time = Time.valueOf(reservation.getTime());
        if (dao.existsByDateAndTime(date, time)) {
            throw new RuntimeException("해당 날짜에는 이미 예약이 존재합니다.");
        }
        ReservationDTO dto = ReservationMapper.INSTANCE.toDto(reservation);

        return dao.insert(dto);
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
