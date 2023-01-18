package nextstep.reservation.service;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.request.ReservationRequestDto;
import nextstep.reservation.dto.response.ReservationResponseDto;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exceptions.exception.DoesNotCreateDataException;
import nextstep.reservation.exceptions.exception.DuplicateReservationTimeException;
import nextstep.reservation.exceptions.exception.DuplicateReservationNameException;
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
        validateReservation(requestDto);
        Theme theme = themeRepository.findById(requestDto.getThemeId()).orElseThrow(
                NotFoundObjectException::new);
        Long id = reservationRepository.add(
                Reservation.builder()
                        .date(requestDto.getDate())
                        .time(requestDto.getTime())
                        .name(requestDto.getName())
                        .theme(theme)
                        .build());
        if (id == -1L) {
            throw new DoesNotCreateDataException();
        }
        return id;
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

    public void validateReservation(ReservationRequestDto requestDto) {
        validateDuplicateTimeReservation(requestDto.getDate(), requestDto.getTime());
        validateDuplicatedNameReservation(requestDto.getName());
    }
    public void validateDuplicateTimeReservation(LocalDate date, LocalTime time) {
        reservationRepository.getReservationByDateAndTime(date, time)
                .ifPresent(reservation -> {
                    throw new DuplicateReservationTimeException();
                });
    }

    public void validateDuplicatedNameReservation(String name) {
        reservationRepository.getReservationByName(name)
                .ifPresent(reservation -> {
                    throw new DuplicateReservationNameException(name + " 으로 된 예약이 이미 존재합니다.");
                });
    }
}
