package nextstep.service;

import nextstep.Reservation;
import nextstep.Theme;
import nextstep.dto.ReservationDto;
import nextstep.repository.ReservationDao;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Long reserve(Reservation reservation) {
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        reservation.setTheme(theme);
        reservationDao.save(reservation);

        return reservation.getId();
    }

    public ReservationDto retrieve(Long id) {
        Reservation reservation = reservationDao.findById(id);
        return new ReservationDto(
                reservation.getId(),
                reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                reservation.getTheme().getName(),
                reservation.getTheme().getDesc(),
                reservation.getTheme().getPrice()
        );
    }
}
