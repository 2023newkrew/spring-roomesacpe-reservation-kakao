package reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.model.domain.Theme;
import reservation.model.dto.RequestTheme;
import reservation.respository.ReservationJdbcTemplateRepository;
import reservation.respository.ThemeJdbcTemplateRepository;
import reservation.util.exception.restAPI.DuplicateException;
import reservation.util.exception.restAPI.ExistException;
import reservation.util.exception.restAPI.NotFoundException;

import java.util.List;

import static reservation.util.exception.ErrorMessages.*;

@Service
public class ThemeService {

    private final ThemeJdbcTemplateRepository themeJdbcTemplateRepository;
    private final ReservationJdbcTemplateRepository reservationJdbcTemplateRepository;

    @Autowired
    public ThemeService(ThemeJdbcTemplateRepository themeJdbcTemplateRepository, ReservationJdbcTemplateRepository reservationJdbcTemplateRepository) {
        this.themeJdbcTemplateRepository = themeJdbcTemplateRepository;
        this.reservationJdbcTemplateRepository = reservationJdbcTemplateRepository;
    }

    public Long createTheme(RequestTheme requestTheme) {
        // 같은 이름의 테마가 존재하는지 validate
        if(themeJdbcTemplateRepository.checkDuplicateName(requestTheme.getName())){
            throw new DuplicateException(THEME_DUPLICATED);
        }
        return themeJdbcTemplateRepository.save(changeToTheme(requestTheme));
    }

    public List<Theme> getAllTheme() {
        return themeJdbcTemplateRepository.findAll();
    }

    public void deleteTheme(Long id) {
        // 해당 id 값을 가지는 테마가 존재하는가?
        if(!themeJdbcTemplateRepository.checkExistById(id)){
            throw new NotFoundException(THEME_NOT_FOUND);
        }

        // 해당 테마를 가지고 있는 예약이 있다면 예약 불가능 (요구 조건)
        if(reservationJdbcTemplateRepository.existByThemeId(id)){
            throw new ExistException(THEME_RESERVATION_EXIST);
        }

        // 테마 삭제
        themeJdbcTemplateRepository.deleteById(id);
    }

    private Theme changeToTheme(RequestTheme requestTheme) {
        return new Theme(0L, requestTheme.getName(), requestTheme.getDesc(), requestTheme.getPrice());
    }
}
