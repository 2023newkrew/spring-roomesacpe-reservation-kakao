package reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.domain.Reservation;
import reservation.respository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void createReservation(Reservation reservation) {
        // 예외 처리
        // - 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

        reservationRepository.createReservation(reservation);

    }

    public Reservation getReservation(Long reservationId) {
        // Reservation 조회
        return null;
    }

    public void deleteReservation(Long reservationId) {
        // Reservation 삭제
    }
}
