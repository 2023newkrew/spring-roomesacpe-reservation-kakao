package nextstep.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.dto.ReservationRequestDTO;
import nextstep.repository.ReservationRepository;
import nextstep.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository repository;

    @Override
    public Long create(ReservationRequestDTO request) {
        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime time = LocalTime.parse(request.getTime() + ":00");
        String name = request.getName();
        Long theme_id = request.getTheme_id();
        Reservation reservation = new Reservation(null, date, time, name, theme_id);
        Long id = repository.insertIfNotExistsDateTime(reservation);
        if(Objects.isNull(id)){
            throw new IllegalArgumentException("해당 시간에 이미 예약이 존재합니다.");
        }

        return id;
    }

    @Override
    public Reservation getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
