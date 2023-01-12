package kakao.service;

import domain.ThemeFactory;
import kakao.dto.request.CreateThemeRequest;
import kakao.dto.request.UpdateThemeRequest;
import kakao.dto.response.ThemeResponse;
import kakao.repository.ThemeJDBCRepository;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final ThemeJDBCRepository themeJDBCRepository;
    private final ThemeFactory themeFactory;

    public ThemeService(ThemeJDBCRepository themeJDBCRepository) {
        this.themeJDBCRepository = themeJDBCRepository;
        this.themeFactory = new ThemeFactory(themeJDBCRepository);
    }

    public long createTheme(CreateThemeRequest request) {
        return themeJDBCRepository.save(themeFactory.create(request));
    }

    public ThemeResponse getTheme(long id) {
        return new ThemeResponse(themeJDBCRepository.findById(id));
    }

    public ThemeResponse updateTheme(UpdateThemeRequest updateRequest) {
        themeJDBCRepository.update(updateRequest);
        return getTheme(updateRequest.id);
    }

    public int delete(long id) {
        return themeJDBCRepository.delete(id);
    }
}
