package nextstep.web.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.dto.ReservationRequestDto;
import nextstep.web.dto.ReservationResponseDto;
import nextstep.web.repository.ReservationRepository;
import nextstep.web.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ThemeRepository themeRepository;

    @Autowired
    public ReservationService(
            @Qualifier("reservationJdbcRepository") ReservationRepository reservationRepository,
            @Qualifier("themeJdbcRepository") ThemeRepository themeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Long createReservation(ReservationRequestDto requestDto) {
        Theme theme = themeRepository.findById(requestDto.getThemeId());

        Reservation reservation = new Reservation(
                requestDto.getDate(),
                requestDto.getTime(),
                requestDto.getName(),
                theme
        );

        return reservationRepository.save(reservation);
    }

    public ReservationResponseDto findReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);

        return ReservationResponseDto.of(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
