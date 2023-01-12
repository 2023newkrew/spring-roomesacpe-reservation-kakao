package domain;

import kakao.dto.request.CreateThemeRequest;
import kakao.error.exception.DuplicatedThemeException;
import kakao.repository.ThemeJDBCRepository;

public class ThemeFactory {
    private final ThemeJDBCRepository themeJDBCRepository;

    public ThemeFactory(ThemeJDBCRepository themeJDBCRepository) {
        this.themeJDBCRepository = themeJDBCRepository;
    }

    public Theme create(CreateThemeRequest request) {
        if (themeJDBCRepository.findByName(request.name).size() > 0)
            throw new DuplicatedThemeException();

        return new Theme(request.name, request.desc, request.price);
    }
}
