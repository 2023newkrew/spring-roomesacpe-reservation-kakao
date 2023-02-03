package nextstep.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import nextstep.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository repository;
    private final ThemeRepository themeRepository;

    @Override
    public Long create(ReservationRequestDTO request) {
        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime time = LocalTime.parse(request.getTime() + ":00");
        String name = request.getName();
        Long theme_id = request.getTheme_id();

        if (repository.existsByDateTime(date, time)) {
            throw new IllegalArgumentException("해당 시간에 이미 예약이 존재합니다");
        }

        Theme theme = themeRepository.getById(theme_id);

        if (theme == null) {
            throw new IllegalArgumentException("존재하지 않는 테마입니다");
        }

        Reservation reservation = new Reservation(date, time, name, theme_id);
        Long id = repository.insert(reservation);

        return id;
    }

    @Override
    public ReservationResponseDTO getById(Long id) {
        Reservation reservation = repository.getById(id);

        if (reservation == null) {
            throw new IllegalArgumentException("잘못된 예약 번호입니다");
        }

        Theme theme = themeRepository.getById(reservation.getTheme_id());

        return new ReservationResponseDTO(reservation, theme);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
