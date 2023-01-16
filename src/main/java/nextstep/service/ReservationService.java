package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.ReservationRequest;
import nextstep.dto.ReservationResponse;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeJdbcRepository;
import nextstep.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Reservation newReservation(ReservationRequest reservationRequest){

        if (reservationRepository.duplicate(reservationRequest.getDate(), reservationRequest.getTime())) {
            throw new IllegalArgumentException();
        }

        Reservation reservation = new Reservation();
        reservation.setTime(reservationRequest.getTime());
        reservation.setDate(reservationRequest.getDate());
        reservation.setName(reservationRequest.getName());
        reservation.setThemeId(reservationRequest.getThemeId());
        return this.reservationRepository.create(reservation);
    }

    public ReservationResponse findReservation(long id){
        Reservation result = this.reservationRepository.find(id);
        Theme theme = this.themeRepository.find(result.getThemeId());
        return new ReservationResponse(result.getId(),result.getDate(),result.getTime(),result.getName(),theme);
    }

    public boolean deleteReservation(long id){
        return this.reservationRepository.delete(id);
    }
}
