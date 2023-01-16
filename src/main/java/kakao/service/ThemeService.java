package kakao.service;

import domain.Theme;
import domain.ThemeValidator;
import kakao.dto.request.CreateThemeRequest;
import kakao.dto.request.UpdateThemeRequest;
import kakao.dto.response.ThemeResponse;
import kakao.repository.ReservationJDBCRepository;
import kakao.repository.ThemeJDBCRepository;
import kakao.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ThemeValidator validator;

    public ThemeService(ThemeJDBCRepository themeRepository, ReservationJDBCRepository reservationJDBCRepository) {
        this.themeRepository = themeRepository;
        validator = new ThemeValidator(themeRepository, reservationJDBCRepository);
    }

    public long createTheme(CreateThemeRequest request) {
        validator.validateForSameName(request.name);

        return themeRepository.save(new Theme(
                request.name,
                request.desc,
                request.price
        ));
    }

    public List<ThemeResponse> getThemes() {
        return themeRepository.themes()
                .stream().
                map(ThemeResponse::new).
                collect(Collectors.toList());
    }

    public ThemeResponse getTheme(long id) {
        return new ThemeResponse(themeRepository.findById(id));
    }

    public ThemeResponse updateTheme(UpdateThemeRequest updateRequest) {
        validator.validateForUsingTheme(updateRequest.id);
        themeRepository.update(updateRequest.name, updateRequest.desc, updateRequest.price, updateRequest.id);
        
        return getTheme(updateRequest.id);
    }

    public int delete(long id) {
        validator.validateForUsingTheme(id);

        return themeRepository.delete(id);
    }
}
