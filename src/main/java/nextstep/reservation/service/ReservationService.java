package nextstep.reservation.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ReservationRequestDto;
import nextstep.reservation.dto.ReservationResponseDto;
import nextstep.reservation.exceptions.exception.DuplicateReservationException;
import nextstep.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository repository;

    public Long addReservation(final ReservationRequestDto requestDto) {
        if (isDuplicatedReservation(requestDto)) {
            throw new DuplicateReservationException();
        }
        return repository.add(requestDto.toEntity());
    }

    public ReservationResponseDto getReservation(final Long id) {
        return new ReservationResponseDto(
                repository.findById(id)
                        .orElseThrow(IllegalArgumentException::new)
        );
    }

    public void deleteReservation(final Long id) {
        repository.delete(id);
    }

    public List<ReservationResponseDto> getAllReservation() {
        return repository.findAll()
                .stream()
                .map(ReservationResponseDto::new)
                .collect(Collectors.toList());
    }

    public boolean isDuplicatedReservation(ReservationRequestDto requestDto) {
        return repository.findAll()
                .stream()
                .anyMatch(
                        reservation -> reservation.getDate().equals(requestDto.getDate())
                                && reservation.getTime().equals(requestDto.getTime())
                );
    }
}
