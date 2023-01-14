package roomescape.service.Reservation;

import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.Reservation.JdbcReservationRepository;

import java.util.Optional;

@Service
@Qualifier("WebReservation")
public class WebReservationService implements ReservationService{
    final JdbcReservationRepository jdbcReservationRepository;

    @Autowired
    public WebReservationService(JdbcReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        if (jdbcReservationRepository.findIdByDateAndTime(reservation) == 1) {
            throw new DuplicateRequestException("요청 날짜/시간에 이미 예약이 있습니다.");
        }
        Long reserveId = jdbcReservationRepository.createReservation(reservation);
        if (reserveId > 0){
            System.out.println(reservation.getName() + "님의 예약이 등록되었습니다.");
            return new Reservation(reserveId, reservation.getDate(), reservation.getTime(),
                    reservation.getName(), reservation.getThemeId());
        }
        System.out.println("NOT REGISTERED");
        return new Reservation();
    }

    @Override
    public Reservation lookUpReservation(Long reserveId) {
        Optional<Reservation> reservation = jdbcReservationRepository.findById(reserveId);
        if (reservation.isPresent()) {
            System.out.println("FOUND");
            return reservation.get();
        }
        System.out.println("NOT FOUND");
        return new Reservation();
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
