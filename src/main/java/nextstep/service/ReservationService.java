package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.dto.ReservationDTO;
import nextstep.dto.ReservationVO;
import nextstep.domain.Theme;
import nextstep.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private Theme theme = new Theme("워너고홈", "병맛", 29_000);

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation newReservation(ReservationDTO reservationDTO){

        if (reservationRepository.duplicate(reservationDTO.getDate(), reservationDTO.getTime())) {
            throw new IllegalArgumentException();
        }

        Reservation reservation = new Reservation();
        reservation.setTime(reservationDTO.getTime());
        reservation.setDate(reservationDTO.getDate());
        reservation.setName(reservationDTO.getName());
        reservation.setTheme(theme);
        return this.reservationRepository.create(reservation);
    }

    public ReservationVO findReservation(long id){
        Reservation result = this.reservationRepository.find(id);
        return new ReservationVO(result.getId(),result.getDate(),result.getTime(),result.getName(),result.getTheme());
    }

    public boolean deleteReservation(long id){
        return this.reservationRepository.delete(id);
    }
}
