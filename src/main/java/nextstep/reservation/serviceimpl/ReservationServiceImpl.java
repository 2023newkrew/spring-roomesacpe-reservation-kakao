package nextstep.reservation.serviceimpl;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.Theme;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.repository.ReservationRepository;
import nextstep.reservation.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private final ReservationRepository repository;

    @Override
    public Long create(ReservationRequest request) {
        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime time = LocalTime.parse(request.getTime() + ":00");
        String name = request.getName();
        Reservation reservation = new Reservation(null, date, time, name, theme);
        Long id = repository.insertIfNotExistsDateTime(reservation);
        if (Objects.isNull(id)) {
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
