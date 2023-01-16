package kakao.service;

import domain.Theme;
import domain.ThemeValidator;
import kakao.dto.request.CreateThemeRequest;
import kakao.dto.request.UpdateThemeRequest;
import kakao.dto.response.ThemeResponse;
import kakao.repository.ReservationJDBCRepository;
import kakao.repository.ThemeJDBCRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private final ThemeJDBCRepository themeJDBCRepository;
    private final ThemeValidator validator;

    public ThemeService(ThemeJDBCRepository themeJDBCRepository, ReservationJDBCRepository reservationJDBCRepository) {
        this.themeJDBCRepository = themeJDBCRepository;
        this.validator = new ThemeValidator(themeJDBCRepository, reservationJDBCRepository);
    }

    public long createTheme(CreateThemeRequest request) {
        validator.validateForCreate(request);
        return themeJDBCRepository.save(new Theme(
                request.name,
                request.desc,
                request.price
        ));
    }

    public List<ThemeResponse> getThemes() {
        return themeJDBCRepository.themes()
                .stream().
                map(ThemeResponse::new).
                collect(Collectors.toList());
    }

    public ThemeResponse getTheme(long id) {
        return new ThemeResponse(themeJDBCRepository.findById(id));
    }

    public ThemeResponse updateTheme(UpdateThemeRequest updateRequest) {
        validator.validateForUpdate(updateRequest.id);
        themeJDBCRepository.update(updateRequest.getUpdateSQL());
        return getTheme(updateRequest.id);
    }

    public int delete(long id) {
        return themeJDBCRepository.delete(id);
    }
}
