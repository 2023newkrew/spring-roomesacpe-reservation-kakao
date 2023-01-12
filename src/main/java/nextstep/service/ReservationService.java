package nextstep.service;

import nextstep.dto.Reservation;
import nextstep.dto.ReservationInput;
import nextstep.dto.Theme;
import nextstep.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private Theme theme = new Theme("워너고홈", "병맛", 29_000);

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation newReservation(ReservationInput reservationInput){

        if (reservationRepository.duplicate(reservationInput)) {
            throw new IllegalArgumentException();
        }

        Reservation reservation = new Reservation();
        reservation.setTime(reservationInput.getTime());
        reservation.setDate(reservationInput.getDate());
        reservation.setName(reservationInput.getName());
        reservation.setTheme(theme);
        return this.reservationRepository.create(reservation);
    }

    public Reservation findReservation(long id){
        return this.reservationRepository.find(id);
    }

    public boolean deleteReservation(long id){
        return this.reservationRepository.delete(id);
    }
}
