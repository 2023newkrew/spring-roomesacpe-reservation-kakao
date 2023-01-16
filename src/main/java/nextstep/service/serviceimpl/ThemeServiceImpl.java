package nextstep.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import nextstep.Theme;
import nextstep.dto.ThemeRequestDTO;
import nextstep.repository.ThemeRepository;
import nextstep.service.ThemeService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ThemeServiceImpl implements ThemeService {
    private final ThemeRepository repository;

    @Override
    public Long create(ThemeRequestDTO request) {
        String name = request.getName();
        String desc = request.getDesc();
        int price = request.getPrice();

        Theme theme = new Theme(null, name, desc, price);
        Long id = repository.insert(theme);

        return id;
    }

    @Override
    public List<Theme> getList() {
        return repository.getList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
