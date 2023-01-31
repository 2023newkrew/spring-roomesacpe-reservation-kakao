package nextstep.domain.theme.service;

import nextstep.domain.reservation.domain.Reservation;
import nextstep.domain.reservation.repository.ReservationRepository;
import nextstep.domain.theme.domain.Theme;
import nextstep.domain.theme.dto.ThemeRequestDto;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.domain.theme.exception.DuplicatedNameThemeException;
import nextstep.domain.theme.exception.ReservedThemeModifyException;
import nextstep.domain.theme.exception.ThemeNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    public ThemeService(ThemeRepository themeRepository,
                        @Qualifier("reservationJdbcTemplateRepository") ReservationRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    public Long add(ThemeRequestDto themeRequestDto) {
        if (themeRepository.findByName(themeRequestDto.getName()).isPresent()) {
            throw new DuplicatedNameThemeException();
        }

        Theme theme = new Theme(
                themeRequestDto.getName(),
                themeRequestDto.getDesc(),
                themeRequestDto.getPrice()
        );
        return themeRepository.save(theme);
    }

    public Theme retrieve(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(ThemeNotFoundException::new);
    }

    public List<Theme> retrieveAll() {
        return themeRepository.findAll();
    }

    public void update(Long id, ThemeRequestDto themeRequestDto) {
        themeRepository.findByName(themeRequestDto.getName())
                .ifPresent(v -> {
                    if (!id.equals(v.getId())) {
                        throw new DuplicatedNameThemeException();
                    }
                });

        Theme theme = getValidTheme(id);

        theme.setName(themeRequestDto.getName());
        theme.setDesc(themeRequestDto.getDesc());
        theme.setPrice(themeRequestDto.getPrice());
        themeRepository.update(theme);
    }

    public void delete(Long id) {
        getValidTheme(id);
        themeRepository.delete(id);
    }

    private Theme getValidTheme(Long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(ThemeNotFoundException::new);

        List<Reservation> reservedReservations = reservationRepository.findByTheme(theme.getName());
        if (reservedReservations.stream()
                .anyMatch(v -> v.getDate().isAfter(LocalDate.now()) &&
                        v.getTime().isAfter(LocalTime.now()))) {
            throw new ReservedThemeModifyException();
        }

        return theme;
    }
}

