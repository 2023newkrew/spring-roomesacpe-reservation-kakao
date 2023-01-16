package nextstep.repository;

import lombok.AllArgsConstructor;
import nextstep.Theme;
import nextstep.dao.ThemeDAO;
import nextstep.dto.ThemeDTO;
import nextstep.mapper.ThemeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class ThemeRepositoryImpl implements ThemeRepository {
    private final ThemeDAO dao;

    @Override
    public Long insert(Theme theme) {
        ThemeDTO dto = ThemeMapper.INSTANCE.toDto(theme);

        return dao.insert(dto);
    }

    @Override
    public List<Theme> getList() {
        List<Theme> list = dao.getList().stream()
                .map(dto -> ThemeMapper.INSTANCE.fromDto(dto))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public boolean deleteById(Long id) {
        return dao.deleteById(id);
    }
}
