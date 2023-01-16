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

import static roomescape.utils.Messages.*;

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
        if (jdbcReservationRepository.findCountByDateAndTime(reservation) == 1) {
            logger.error(CREATE_DUPLICATED.getMessage() + RESERVATION_DATE.getMessage() + reservation.getDate() +
                    RESERVATION_TIME.getMessage() + reservation.getTime());
            throw new DuplicateRequestException(RESERVATION_CREATE_ERROR.getMessage());
        }
        Long reserveId = jdbcReservationRepository.createReservation(reservation);
        logger.info(CREATE_SUCCESS.getMessage() + reserveId);
        return new Reservation(reserveId, reservation.getDate(), reservation.getTime(),
                reservation.getName(), reservation.getThemeId());
    }

    @Override
    public Reservation lookUpReservation(Long reserveId) {
        try {
            Optional<Reservation> reservation = jdbcReservationRepository.findReservationById(reserveId);
            if (reservation.isPresent()) {
                return reservation.get();
            }
        } catch (Exception e) {
            logger.error(NOT_FOUND_ERROR.getMessage() + reserveId + ", " + e);
        }
        throw new NullPointerException("요청한 ID " + reserveId + "를 조회할 수 없습니다");
    }

    @Override
    public void deleteReservation(Long deleteId) {
        Integer deleteResult = jdbcReservationRepository.deleteReservation(deleteId);
        if (deleteResult == 0){
            logger.error(DELETE_NOT_FOUND_ERROR.getMessage() + deleteId);
            throw new NullPointerException("요청한 ID " + deleteId + "를 조회할 수 없습니다");
        }
        logger.info(DELETE_SUCCESS.getMessage());
    }
}
