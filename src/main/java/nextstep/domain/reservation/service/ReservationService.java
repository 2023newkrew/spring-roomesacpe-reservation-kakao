package nextstep.domain.reservation.service;

import nextstep.domain.reservation.domain.Reservation;
import nextstep.domain.reservation.dto.ReservationRequestDto;
import nextstep.domain.reservation.dto.ReservationResponseDto;
import nextstep.domain.reservation.repository.ReservationRepository;
import nextstep.domain.theme.domain.Theme;
import nextstep.exceptions.exception.DuplicatedDateAndTimeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(@Qualifier("reservationJdbcTemplateRepository") ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long reserve(ReservationRequestDto reservationRequestDto) {
        LocalDate date = reservationRequestDto.getDate();
        LocalTime time = reservationRequestDto.getTime();

        if (reservationRepository.countByDateAndTime(date, time) > 0) {
            throw new DuplicatedDateAndTimeException();
        }

        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        Reservation newReservation = new Reservation(date, time, reservationRequestDto.getName(), theme);

        return reservationRepository.save(newReservation);
    }

    public ReservationResponseDto retrieve(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation != null) {
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
        return null;
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
