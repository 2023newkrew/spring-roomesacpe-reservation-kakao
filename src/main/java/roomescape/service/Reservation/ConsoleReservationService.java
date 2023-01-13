package roomescape.service.Reservation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;

@Service
@Qualifier("consoleService")
public class ConsoleReservationService implements ReservationService{

    @Override
    public String createReservation(Reservation reservation) {
        return null;
    }

    @Override
    public String lookUpReservation(Long ReserveId) {
        return null;
    }

    @Override
    public void deleteReservation(Long deleteId) {
    }
}
