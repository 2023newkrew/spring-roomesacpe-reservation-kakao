package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.ReservationRequest;
import nextstep.dto.ReservationResponse;
import nextstep.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
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
        return new ReservationResponse(result.getId(),result.getDate(),result.getTime(),result.getName(),new Theme());
    }

    public boolean deleteReservation(long id){
        return this.reservationRepository.delete(id);
    }
}
