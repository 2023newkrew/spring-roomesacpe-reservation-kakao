package reservation.service;

import reservation.domain.Theme;
import org.springframework.stereotype.Service;
import reservation.domain.Reservation;
import reservation.domain.dto.ReservationDto;
import reservation.util.exception.DuplicateException;
import reservation.respository.ReservationRepository;
import reservation.util.exception.NotFoundException;

import static reservation.util.ErrorStatus.RESERVATION_DUPLICATED;
import static reservation.util.ErrorStatus.RESERVATION_NOT_FOUND;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(ReservationDto reservationDto) {
        if(reservationRepository.existByDateTime(reservationDto.getDate(), reservationDto.getTime())){
            throw new DuplicateException(RESERVATION_DUPLICATED);
        }
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        return reservationRepository.createReservation(reservationDto, theme);
    }

    public Reservation getReservation(Long id) {
        if(!reservationRepository.existById(id)){
            throw new NotFoundException(RESERVATION_NOT_FOUND);
        }
        return reservationRepository.getReservation(id);
    }

    public void deleteReservation(Long id) {
        if(!reservationRepository.existById(id)){
            throw new NotFoundException(RESERVATION_NOT_FOUND);
        }
        reservationRepository.deleteReservation(id);
    }
}
