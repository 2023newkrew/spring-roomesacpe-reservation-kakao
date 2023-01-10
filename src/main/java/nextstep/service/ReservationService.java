package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.domain.repository.ReservationRepository;
import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservationResponse;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {

    private static final Theme THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(CreateReservationRequest createReservationRequest) {
        LocalDate date = LocalDate.parse(createReservationRequest.getDate());
        LocalTime time = LocalTime.parse(createReservationRequest.getTime());

        if (reservationRepository.existsByDateAndTime(date, time)) {
            throw new DuplicateReservationException();
        }

        Reservation savedReservation = reservationRepository.save(new Reservation(date, time, createReservationRequest.getName(), THEME));
        return savedReservation.getId();
    }

    public FindReservationResponse findReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        return FindReservationResponse.from(reservation);
    }

    public void deleteReservationById(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
