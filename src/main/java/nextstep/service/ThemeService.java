package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.CreateThemeRequest;
import nextstep.repository.reservation.ReservationRepository;
import nextstep.repository.theme.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository, ReservationRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }


    public Long createTheme(String name, String desc, Integer price) {
        Long savedId = themeRepository.save(name, desc, price);
        return savedId;
    }

    public Long createTheme(Theme theme) {
        return this.createTheme(theme.getName(), theme.getDesc(), theme.getPrice());
    }

    public Long createTheme(CreateThemeRequest request) {
        return this.createTheme(request.getName(), request.getDesc(), request.getPrice());
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public Theme findById(Long themeId) {
        return themeRepository.findById(themeId);
    }

    public void deleteTheme(Long id) {
        List<Reservation> reservations = reservationRepository.findByThemeId(id);
        // 해당 테마로 예약된 모든 예약 삭제
        for (Reservation reservation : reservations) {
            reservationRepository.deleteById(reservation.getId());
        }
        themeRepository.deleteById(id);
    }
}
