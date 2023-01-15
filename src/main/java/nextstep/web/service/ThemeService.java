package nextstep.web.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.dto.ThemeRequestDto;
import nextstep.web.dto.ThemeResponseDto;
import nextstep.web.exception.BusinessException;
import nextstep.web.exception.ThemeErrorCode;
import nextstep.web.repository.ReservationRepository;
import nextstep.web.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ThemeService {

    private final ThemeRepository themeRepository;

    private final ReservationRepository reservationRepository;

    public ThemeService(
            @Qualifier("reservationJdbcRepository") ReservationRepository reservationRepository,
            @Qualifier("themeJdbcRepository") ThemeRepository themeRepository
    ) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Long create(ThemeRequestDto themeRequestDto) {
        Theme theme = Theme.builder()
                .name(themeRequestDto.getName())
                .desc(themeRequestDto.getDesc())
                .price(themeRequestDto.getPrice())
                .build();

        return themeRepository.save(theme);
    }

    public List<ThemeResponseDto> readAll() {
        return themeRepository.findAll()
                .stream()
                .map(ThemeResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findByThemeId(id);
        if (optionalReservation.isPresent()) {
            throw new BusinessException(ThemeErrorCode.THEME_RELATED_WITH_RESERVATION);
        }
        themeRepository.deleteById(id);
    }
}
