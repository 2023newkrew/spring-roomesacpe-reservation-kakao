package nextstep.theme.service;

import lombok.RequiredArgsConstructor;
import nextstep.exception.ConstraintViolationException;
import nextstep.exception.EntityNotFoundException;
import nextstep.reservation.dao.ThemeReservationDao;
import nextstep.theme.dao.ThemeDao;
import nextstep.theme.dto.ThemeDetail;
import nextstep.theme.dto.ThemeDto;
import nextstep.theme.entity.Theme;
import nextstep.theme.exception.ThemeErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ThemeServiceImpl implements ThemeService{
    private final ThemeDao themeDao;
    private final ThemeReservationDao themeReservationDao;

    @Override
    public ThemeDetail findById(Long id) {
        Theme theme = themeDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ThemeErrorCode.THEME_NOT_FOUND));

        return new ThemeDetail(theme);
    }

    @Override
    public List<ThemeDetail> findAll() {
        return themeDao.findAll().stream()
                .map(ThemeDetail::new)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(ThemeDto themeDto) {
        if(isThemeNamePresent(themeDto)){
            throw new ConstraintViolationException(ThemeErrorCode.THEME_NAME_CANNOT_BE_DUPLICATED);
        }
        Theme theme = themeDto.toEntity();
        themeDao.insert(theme);

        return theme.getId();
    }

    @Override
    public int update(ThemeDto themeDto) {
        if(isThemeHasReservation(themeDto.getId())){
            throw new ConstraintViolationException(ThemeErrorCode.THEME_WITH_RESERVATIONS_CANNOT_MODIFY_OR_DELETE);
        }

        return themeDao.update(themeDto.toEntity());
    }

    @Override
    public void deleteById(Long id) {
        if(isThemeHasReservation(id)){
            throw new ConstraintViolationException(ThemeErrorCode.THEME_WITH_RESERVATIONS_CANNOT_MODIFY_OR_DELETE);
        }

        if(!isThemePresent(id)){
            throw new EntityNotFoundException(ThemeErrorCode.THEME_NOT_FOUND);
        }
        themeDao.deleteById(id);
    }

    private boolean isThemePresent(Long id) {
        return themeDao.findById(id).isPresent();
    }

    private boolean isThemeNamePresent(ThemeDto themeDto) {
        return themeDao.findByName(themeDto.getName()).isPresent();
    }

    private boolean isThemeHasReservation(Long themeId){
        return !themeReservationDao.findByThemeId(themeId).isEmpty();
    }
}
