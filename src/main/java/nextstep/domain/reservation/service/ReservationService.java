package nextstep.domain.reservation.service;

import nextstep.domain.reservation.domain.Reservation;
import nextstep.domain.reservation.dto.ReservationRequestDto;
import nextstep.domain.reservation.dto.ReservationResponseDto;
import nextstep.domain.reservation.repository.ReservationRepository;
import nextstep.domain.theme.domain.Theme;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.global.exceptions.exception.DuplicatedDateAndTimeException;
import nextstep.global.exceptions.exception.ReservationNotFoundException;
import nextstep.global.exceptions.exception.ThemeNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    public final ThemeRepository themeRepository;

    public ReservationService(@Qualifier("reservationJdbcTemplateRepository") ReservationRepository reservationRepository,
                              ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Long reserve(ReservationRequestDto reservationRequestDto) {
        LocalDate date = reservationRequestDto.getDate();
        LocalTime time = reservationRequestDto.getTime();

        if (reservationRepository.countByDateAndTime(date, time) > 0) {
            throw new DuplicatedDateAndTimeException();
        }

        Theme theme = themeRepository.findById(reservationRequestDto.getThemeId())
                .orElseThrow(ThemeNotFoundException::new);
        Reservation newReservation = new Reservation(date, time, reservationRequestDto.getName(), theme);

        return reservationRepository.save(newReservation);
    }

    public ReservationResponseDto retrieve(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);

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
        reservationRepository.delete(id);
    }
}
