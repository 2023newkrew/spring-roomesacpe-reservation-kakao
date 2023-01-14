package roomescape.service.Theme;

import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.repository.Theme.JdbcThemeRepository;

import java.util.Optional;

@Service
@Qualifier("WebTheme")
public class WebThemeService implements ThemeService {
    JdbcThemeRepository jdbcThemeRepository;

    public WebThemeService(JdbcThemeRepository jdbcThemeRepository) {
        this.jdbcThemeRepository = jdbcThemeRepository;
    }

    @Override
    public Theme createTheme(Theme theme) {
        if (jdbcThemeRepository.findIdByDateAndTime(theme) == 1) {
            throw new DuplicateRequestException("요청한 이름/가격의 테마가 이미 등록되어 있습니다.");
        }
        Long themeId = jdbcThemeRepository.createTheme(theme);
        if (themeId > 0){
            System.out.println(theme.getName() + "님의 테마가 등록되었습니다.");
            return new Theme(themeId, theme.getName(), theme.getDesc(), theme.getPrice());
        }
        return new Theme();
    }

    @Override
    public Theme lookUpTheme(Long themeId) {
        Optional<Theme> theme = jdbcThemeRepository.findById(themeId);
        if (theme.isPresent()) {
            // 객체 타입이 아닌, 특정 타입으로 wrapping 필요
            return theme.get();
        }
        System.out.println(themeId + "NOT FOUND");
        return new Theme();
    }

    @Override
    public void deleteTheme(Long deleteId) {
        Integer deleteResult = jdbcThemeRepository.deleteTheme(deleteId);
        if (deleteResult != 1){
            throw new IllegalArgumentException("존재하지 않는 테마 ID 입니다.");
        }
        System.out.println("Id: " + deleteResult + "의 테마가 삭제되었습니다.");
    }
}
