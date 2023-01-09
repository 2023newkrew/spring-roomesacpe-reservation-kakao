package nextstep.service;

import nextstep.Reservation;
import nextstep.Theme;
import nextstep.dto.CreateReservationRequest;
import nextstep.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class RoomEscapeService {
    public static final Theme DEFAULT_THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private final ReservationRepository reservationRepository;


    @Autowired
    public RoomEscapeService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation add(CreateReservationRequest request) {
        Reservation reservation = new Reservation(
                LocalDate.parse(request.getDate()),
                LocalTime.parse(request.getTime() + ":00"),
                request.getName(),
                DEFAULT_THEME
        );
        return reservationRepository.add(reservation);
    }

    public Reservation get(Long id) {
        return reservationRepository.get(id);
    }
}
