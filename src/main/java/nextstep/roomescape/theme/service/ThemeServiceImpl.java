package nextstep.roomescape.theme.service;

import nextstep.roomescape.exception.NotExistEntityException;
import nextstep.roomescape.theme.controller.dto.ThemeRequestDTO;
import nextstep.roomescape.theme.repository.ThemeRepository;
import nextstep.roomescape.theme.repository.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    @Autowired
    public ThemeServiceImpl(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @Override
    public Long create(ThemeRequestDTO themeRequestDTO) {
        return themeRepository.create(themeRequestDTO.toEntity());
    }

    @Override
    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    @Override
    public void delete(long id) {
        Optional<Theme> theme = themeRepository.findById(id);
        if (theme.isEmpty()){
            throw new NotExistEntityException("해당 id 테마는 존재하지 않습니다.");
        }
        themeRepository.delete(id);
    }
}
