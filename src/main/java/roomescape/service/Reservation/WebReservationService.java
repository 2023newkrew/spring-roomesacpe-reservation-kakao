package roomescape.service.Reservation;

import com.sun.jdi.request.DuplicateRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger =
            LoggerFactory.getLogger(WebReservationService.class);

    @Autowired
    public WebReservationService(JdbcReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        if (jdbcReservationRepository.findIdByDateAndTime(reservation) == 1) {
            logger.error("Reservation createDuplicatedError," +
                    "date:" + reservation.getDate() +", time:" + reservation.getTime());
            throw new DuplicateRequestException("요청 날짜/시간에 이미 예약이 있습니다.");
        }
        Long reserveId = jdbcReservationRepository.createReservation(reservation);
        if (reserveId > 0){
            logger.info("Reservation createReservation, Id: " + reserveId);
            return new Reservation(reserveId, reservation.getDate(), reservation.getTime(),
                    reservation.getName(), reservation.getThemeId());
        }
        logger.error("Reservation create QueryError");
        return new Reservation();
    }

    @Override
    public Reservation lookUpReservation(Long reserveId) {
        Optional<Reservation> reservation = jdbcReservationRepository.findReservationById(reserveId);
        if (reservation.isPresent()) {
            return reservation.get();
        }
        logger.error("Reservation findError, NotFound Id: " + reserveId);
        return new Reservation();
    }

    @Override
    public void deleteReservation(Long deleteId) {
        Integer deleteResult = jdbcReservationRepository.deleteReservation(deleteId);
        if (deleteResult != 1){
            logger.error("Reservation DeleteError, NotFound Id: " + deleteId);
            throw new IllegalArgumentException("존재하지 않는 예약 ID 입니다.");
        }
        logger.info("Reservation DeleteSuccess Id: " + deleteResult);
    }
}
