package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.ThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.exception.InvalidRequestException;
import nextstep.repository.ThemeDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private ThemeDao themeDao;

    public ThemeService(@Qualifier("themeJdbcTemplateDao") ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(ThemeRequest themeRequest) {
        validateNameDuplication(themeRequest.getName());
        Theme theme = new Theme(
                themeRequest.getName(),
                themeRequest.getDesc(),
                themeRequest.getPrice()
        );
        return themeDao.save(theme);
    }

    private void validateNameDuplication(String name) {
        if (themeDao.findByName(name).isPresent()) {
            throw new InvalidRequestException(ErrorCode.THEME_NAME_DUPLICATED);
        }
    }

    public ThemeResponse retrieveOne(Long id) {
        Optional<Theme> themeFound = themeDao.findById(id);
        if (themeFound.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.THEME_NOT_FOUND);
        }
        return new ThemeResponse(themeFound.get());
    }

    public List<ThemeResponse> retrieveAll() {
        return themeDao.findAll()
                .stream()
                .map(ThemeResponse::new)
                .collect(Collectors.toList());
    }

    public Long update(Long id, ThemeRequest themeRequest) {
        validateId(id);
        Optional<Theme> themeFound = themeDao.findById(id);
        if (themeFound.isEmpty()) {
            return create(themeRequest);
        }
        if (!themeFound.get().getName().equals(themeRequest.getName())) {
            validateNameDuplication(themeRequest.getName());
        }
        Theme theme = new Theme(id, themeRequest.getName(), themeRequest.getDesc(), themeRequest.getPrice());
        themeDao.update(theme);
        return id;
    }

    private void validateId(Long id) {
        if (id <= 0) {
            throw new InvalidRequestException(ErrorCode.INPUT_PARAMETER_INVALID);
        }
    }

    public void delete(Long id) {
        validateId(id);
        if (themeDao.findById(id).isEmpty()) {
            throw new InvalidRequestException(ErrorCode.THEME_NOT_FOUND);
        }
        themeDao.delete(id);
    }
}
