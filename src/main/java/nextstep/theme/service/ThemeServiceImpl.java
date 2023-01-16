package nextstep.theme.service;

import lombok.RequiredArgsConstructor;
import nextstep.etc.exception.ErrorMessage;
import nextstep.etc.exception.ThemeConflictException;
import nextstep.theme.domain.Theme;
import nextstep.theme.dto.ThemeRequest;
import nextstep.theme.dto.ThemeResponse;
import nextstep.theme.mapper.ThemeMapper;
import nextstep.theme.repository.ThemeRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository repository;

    private final ThemeMapper mapper;

    @Transactional
    @Override
    public ThemeResponse create(ThemeRequest request) {
        var theme = mapper.fromRequest(request);
        try {
            theme = repository.insert(theme);
        }
        catch (DuplicateKeyException ignore) {
            throw new ThemeConflictException(ErrorMessage.THEME_CONFLICT);
        }

        return mapper.toResponse(theme);
    }

    @Override
    public ThemeResponse getById(Long id) {
        Theme theme = repository.getById(id);

        return mapper.toResponse(theme);
    }

    @Override
    public List<ThemeResponse> getAll() {
        return null;
    }

    @Override
    public ThemeResponse update(Long id, ThemeRequest request) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
