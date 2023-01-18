package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.repository.common.ReservationRepository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationDto;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long addReservation(ReservationDto reservationDto) {
        if (checkExistence(reservationDto.getDate(), reservationDto.getTime()))
            throw new IllegalArgumentException("이미 있는 시간임");
        return reservationRepository.add(new Reservation(reservationDto)).getId();
    }

    public Reservation getReservation(Long id) {
        if (!checkExistence(id)) throw new IllegalArgumentException("없는 아이디");
        return reservationRepository.get(id);
    }

    public void removeReservation(Long id) {
        if (!checkExistence(id)) throw new IllegalArgumentException("없는 아이디");
        reservationRepository.remove(id);
    }

    private boolean checkExistence(String date, String time) {
        return reservationRepository.get(date, time) != null;
    }

    private boolean checkExistence(Long id) {
        return reservationRepository.get(id) != null;
    }
}
