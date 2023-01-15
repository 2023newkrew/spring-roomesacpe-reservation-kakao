package nextstep.reservation.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.request.ReservationRequestDto;
import nextstep.reservation.dto.response.ReservationResponseDto;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exceptions.exception.DuplicateReservationException;
import nextstep.reservation.exceptions.exception.NotFoundObjectException;
import nextstep.reservation.repository.reservation.ReservationRepository;
import nextstep.reservation.repository.theme.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;


    @Transactional
    public Long addReservation(final ReservationRequestDto requestDto) {
        if (isDuplicatedReservation(requestDto)) {
            throw new DuplicateReservationException();
        }
        Theme theme = themeRepository.findById(requestDto.getThemeId()).orElseThrow(
                NotFoundObjectException::new);
        return reservationRepository.add(
                Reservation.builder()
                        .date(requestDto.getDate())
                        .time(requestDto.getTime())
                        .name(requestDto.getName())
                        .theme(theme)
                        .build()
        );
    }

    public ReservationResponseDto getReservation(final Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(NotFoundObjectException::new);
        return new ReservationResponseDto(reservation);
    }

    public void deleteReservation(final Long id) {
        reservationRepository.delete(id);
    }

    public List<ReservationResponseDto> getAllReservation() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponseDto::new)
                .collect(Collectors.toList());
    }

    public boolean isDuplicatedReservation(ReservationRequestDto requestDto) {
        return reservationRepository.findAll()
                .stream()
                .anyMatch(
                        reservation -> reservation.getDate().equals(requestDto.getDate())
                                && reservation.getTime().equals(requestDto.getTime())
                );
    }
}
