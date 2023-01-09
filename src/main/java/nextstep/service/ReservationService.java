package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Reservations;
import nextstep.domain.Theme;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservationResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {

    private static final Theme THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private final Reservations reservations;

    public ReservationService(Reservations reservations) {
        this.reservations = reservations;
    }

    public Long createReservation(CreateReservationRequest createReservationRequest) {
        LocalDate date = LocalDate.parse(createReservationRequest.getDate());
        LocalTime time = LocalTime.parse(createReservationRequest.getTime());

        Reservation savedReservation = reservations.save(new Reservation(date, time, createReservationRequest.getName(), THEME));

        return savedReservation.getId();
    }

    public FindReservationResponse findReservationById(Long reservationId) {
        Reservation reservation = reservations.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 id의 예약 입니다."));

        return FindReservationResponse.from(reservation);
    }
}
