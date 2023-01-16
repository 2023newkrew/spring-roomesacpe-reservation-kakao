package nextstep;

import domain.Theme;
import kakao.dto.request.UpdateThemeRequest;
import kakao.error.ErrorCode;
import kakao.error.exception.RecordNotFoundException;
import kakao.repository.ThemeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ThemeConsoleRepository implements ThemeRepository {
    private final ThemeDAO themeDAO = new ThemeDAO();

    @Override
    public long save(Theme theme) {
        return themeDAO.save(theme);
    }

    @Override
    public List<Theme> themes() {
        return new ArrayList<>();
    }

    @Override
    public Theme findById(long id) {
        Theme result = themeDAO.findById(id);
        if (Objects.isNull(result)) throw new RecordNotFoundException(ErrorCode.RESERVATION_NOT_FOUND, null);

        return themeDAO.findById(id);
    }

    @Override
    public List<Theme> findByName(String name) {
        return themeDAO.findByName(name);
    }

    @Override
    public int update(String name, String desc, Integer price, long id) {
        return themeDAO.update(new UpdateThemeRequest(id, name, desc, price));
    }

    @Override
    public int delete(long id) {
        return themeDAO.delete(id);
    }
}
