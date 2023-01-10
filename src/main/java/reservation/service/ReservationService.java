package reservation.service;

import reservation.domain.Theme;
import org.springframework.stereotype.Service;
import reservation.domain.Reservation;
import reservation.domain.dto.ReservationDto;
import reservation.exception.ReservationException;
import reservation.respository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(ReservationDto reservationDto) {
        if(reservationRepository.existReservation(reservationDto.getDate(), reservationDto.getTime())){
            throw new ReservationException();
        }
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        return reservationRepository.createReservation(reservationDto, theme);
    }

    public Reservation getReservation(Long reservationId) {
        return reservationRepository.getReservation(reservationId);
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteReservation(reservationId);
    }
}
