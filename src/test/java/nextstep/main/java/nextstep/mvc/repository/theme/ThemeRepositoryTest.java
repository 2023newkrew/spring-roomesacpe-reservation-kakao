package nextstep.main.java.nextstep.mvc.repository.theme;

import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateOrUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.springframework.context.annotation.FilterType.ANNOTATION;

@JdbcTest(includeFilters = @ComponentScan.Filter(type = ANNOTATION, classes = Repository.class))
@Sql(scripts = "/sql/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ThemeRepositoryTest {
    @Autowired
    private ThemeRepository themeRepository;

    @Test
    @DisplayName("[테마 생성] 테마 생성 성공")
    void save() {
        Long id = 1L;
        ThemeCreateOrUpdateRequest request = ThemeCreateOrUpdateRequest.of("theme", "description of new theme", 10000);
        Theme theme = createThemeFromIdAndRequest(id, request);
        Long newId = themeRepository.save(request);

        assertThat(newId).isEqualTo(theme.getId());
    }

    @Test
    @DisplayName("[테마 조회] 테마 조회 성공")
    void find() {
        Long id = 100L;

        assertThat(themeRepository.findById(id)).isPresent();
    }

    @Test
    @DisplayName("[테마 조회] 테마 조회 성공")
    void findNotExists() {
        Long id = 99L;

        assertThat(themeRepository.findById(id)).isEmpty();
    }

    @Test
    @DisplayName("[테마 삭제] 테마 삭제 Delete 쿼리 정상작동 확인")
    void delete() {
        Long id = 100L;

        assertThat(themeRepository.findById(id)).isPresent();
        assertThatCode(() -> themeRepository.deleteById(id)).doesNotThrowAnyException();
        assertThat(themeRepository.findById(id)).isEmpty();
    }

    private Theme createThemeFromIdAndRequest(Long id, ThemeCreateOrUpdateRequest request) {
        return Theme.builder()
                .id(id)
                .name(request.getName())
                .desc(request.getDesc())
                .price(request.getPrice())
                .build();
    }
}
