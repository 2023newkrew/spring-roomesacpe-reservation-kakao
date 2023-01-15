package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.FindReservation;
import nextstep.repository.reservation.ReservationRepository;
import nextstep.repository.theme.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public FindReservation findById(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        Theme theme = themeRepository.findByThemeId(reservation.getThemeId());

        return FindReservation.from(reservation, theme);
    }

    public List<FindReservation> findAll(){
        return reservationRepository.findAll();
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Transactional
    public void deleteReservationByTheme(Long themeId){
        reservationRepository.deleteByThemeId(themeId);
    }

    public Long createReservation(Reservation reservation) {
        Theme theme = themeRepository.findByThemeId(reservation.getThemeId());
        return reservationRepository.save(reservation.getDate(), reservation.getTime(), reservation.getName(), theme);
    }

    @Transactional
    public void resetTable() {
        try {
            reservationRepository.dropTable();
            themeRepository.dropThemeTable();

            reservationRepository.createTable();
            themeRepository.createThemeTable();
        } catch (Exception e){
            throw new RuntimeException("테이블 reset 실패");
        }

    }
}
