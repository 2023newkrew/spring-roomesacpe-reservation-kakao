package nextstep.service;

import nextstep.Reservation;
import nextstep.Theme;
import nextstep.dto.ReservationRequestDto;
import nextstep.dto.ReservationResponseDto;
import nextstep.exceptions.exception.DuplicatedDateAndTimeException;
import nextstep.repository.ReservationDao;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Long reserve(ReservationRequestDto reservationRequestDto) {
        LocalDate date = reservationRequestDto.getDate();
        LocalTime time = reservationRequestDto.getTime();
        if (reservationDao.findByDateAndTime(date, time) != null) {
            throw new DuplicatedDateAndTimeException();
        }
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        Reservation newReservation = new Reservation(date, time, reservationRequestDto.getName(), theme);

        return reservationDao.save(newReservation);
    }

    public ReservationResponseDto retrieve(Long id) {
        Reservation reservation = reservationDao.findById(id);
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                reservation.getTheme().getName(),
                reservation.getTheme().getDesc(),
                reservation.getTheme().getPrice()
        );
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
