package nextstep.service;

import nextstep.dao.ReservationDAO;
import nextstep.dao.ThemeDAO;
import nextstep.domain.Theme;
import nextstep.exceptions.DataNotExistException;
import nextstep.exceptions.JobNotAllowedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeDAO themeDAO;
    private final ReservationDAO reservationDAO;

    public ThemeService(ThemeDAO themeJdbcTemplateDAO, ReservationDAO reservationJdbcTemplateDAO) {
        this.themeDAO = themeJdbcTemplateDAO;
        this.reservationDAO = reservationJdbcTemplateDAO;
    }

    public Long saveTheme(Theme theme) {
        return themeDAO.save(theme);
    }

    public List<Theme> findAllTheme() {
        return themeDAO.findAll();
    }

    public void deleteTheme(Long id) {
        if (reservationDAO.existsByThemeId(id)) {
            throw new JobNotAllowedException("이 테마를 참조하는 예약이 존재하여 테마를 삭제할 수 없습니다.");
        }
        if (themeDAO.deleteById(id) == 0) {
            throw new DataNotExistException("테마가 존재하지 않습니다.");
        }
    }
}
