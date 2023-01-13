package roomescape.service.Reservation;

import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.Reservation.JdbcReservationRepository;

import java.util.Optional;

@Service
@Qualifier("WebReservation")
public class WebReservationService implements ReservationService{
    JdbcReservationRepository jdbcReservationRepository;

    public WebReservationService(JdbcReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @Override
    public String createReservation(Reservation reservation) {
        if (jdbcReservationRepository.findIdByDateAndTime(reservation) == 1) {
            throw new DuplicateRequestException("요청 날짜/시간에 이미 예약이 있습니다.");
        }
        Long reserveId = jdbcReservationRepository.createReservation(reservation);
        if (reserveId > 0){
            System.out.println(reservation.getName() + "님의 예약이 등록되었습니다.");
        }
        return "Location: /reservation/" + reserveId;
    }

    @Override
    public String lookUpReservation(Long reserveId) {
        Optional<Reservation> reservation = jdbcReservationRepository.findById(reserveId);
        if (reservation.isPresent()) {
            return reservation.get().toMessage();
        }
        return "NOT FOUND RESERVATION ID: " + reserveId;
    }

    @Override
    public void deleteReservation(Long deleteId) {
        Integer deleteResult = jdbcReservationRepository.deleteReservation(deleteId);
        if (deleteResult != 1){
            throw new IllegalArgumentException("존재하지 않는 예약 ID 입니다.");
        }
        System.out.println("Id: " + deleteResult + "의 예약이 취소되었습니다.");
    }
}
