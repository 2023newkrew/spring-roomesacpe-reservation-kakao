package nextstep.serviceimpl;

import lombok.RequiredArgsConstructor;
import nextstep.Reservation;
import nextstep.Theme;
import nextstep.dto.ReservationRequest;
import nextstep.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final Map<Long, Reservation> reservations = new HashMap<>();

    private final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private Long reservationIdIndex = 0L;

    @Override
    public Reservation create(ReservationRequest request) {
        Reservation reservation = new Reservation(
                ++reservationIdIndex,
                LocalDate.parse(request.getDate()),
                LocalTime.parse(request.getTime() + ":00"),
                request.getName(),
                theme
        );

        reservations.put(reservationIdIndex, reservation);
        return reservation;
    }

    @Override
    public Reservation getById(Long id) {
        return reservations.get(id);
    }

    @Override
    public void deleteById(Long id) {
        reservations.remove(id);
    }
}
