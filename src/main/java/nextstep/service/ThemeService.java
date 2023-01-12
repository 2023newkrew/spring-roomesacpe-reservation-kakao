package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.ThemeRequest;
import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.exception.InvalidRequestException;
import nextstep.repository.ThemeDao;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    private ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(ThemeRequest themeRequest) {
        if (themeDao.findByName(themeRequest.getName()).isPresent()) {
            throw new InvalidRequestException(ErrorCode.THEME_NAME_DUPLICATED);
        }
        Theme theme = new Theme(
                themeRequest.getName(),
                themeRequest.getDesc(),
                themeRequest.getPrice()
        );
        return themeDao.save(theme);
    }
}
