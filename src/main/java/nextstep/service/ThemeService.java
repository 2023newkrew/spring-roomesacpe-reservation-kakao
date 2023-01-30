package nextstep.service;

import nextstep.domain.Theme;
import nextstep.repository.reservation.ReservationRepository;
import nextstep.repository.theme.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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

    public Theme findById(Long id){
        return themeRepository.findByThemeId(id);
    }

    public List<Theme> findAll(){
        return themeRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws SQLException {
        themeRepository.deleteById(id);
        reservationRepository.deleteByThemeId(id);
    }

    public Long createTheme(Theme theme){
        return themeRepository.save(theme);
    }

    public Theme findByTheme(Theme theme){
        return themeRepository.findByTheme(theme);
    }
}
