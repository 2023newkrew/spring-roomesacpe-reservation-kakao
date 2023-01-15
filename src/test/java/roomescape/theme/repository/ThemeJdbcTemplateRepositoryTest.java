package roomescape.theme.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.theme.domain.Theme;

@JdbcTest
@TestMethodOrder(OrderAnnotation.class)
class ThemeJdbcTemplateRepositoryTest {

    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeJdbcTemplateRepositoryTest(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.themeRepository = new ThemeJdbcTemplateRepository(jdbcTemplate,
                dataSource);
    }

    private static final String expectedName = "name";
    private static final String expectedDesc = "desc";
    private static final int expectedPrice = 1000;
    private static final Long invalidId = -1L;
    private static final String invalidName = "invalid";
    private Long themeId;

    @BeforeEach
    void setUp() {
        final Theme theme = Theme.builder()
                .name(expectedName)
                .desc(expectedDesc)
                .price(expectedPrice)
                .build();

        final Theme savedTheme = themeRepository.save(theme);
        this.themeId = savedTheme.getId();
    }

    @DisplayName("id로 테마를 가져온다")
    @Test
    void findById() {
        final Theme result = this.themeRepository.findById(themeId)
                .orElseThrow(RuntimeException::new);

        assertThat(result.getId()).isEqualTo(themeId);
        assertThat(result.getName()).isEqualTo(expectedName);
        assertThat(result.getDesc()).isEqualTo(expectedDesc);
        assertThat(result.getPrice()).isEqualTo(expectedPrice);
    }

    @DisplayName("id에 해당하는 테마가 없다면 empty 를 반환한다")
    @Test
    void cannotFindWithInvalidId() {
        assertThat(this.themeRepository.findById(invalidId)).isEmpty();
    }

    @DisplayName("테마 이름으로 테마를 가져온다.")
    @Test
    void findByName() {
        final Theme result = this.themeRepository.findByName(expectedName)
                .orElseThrow(RuntimeException::new);

        assertThat(result.getId()).isEqualTo(themeId);
        assertThat(result.getName()).isEqualTo(expectedName);
        assertThat(result.getDesc()).isEqualTo(expectedDesc);
        assertThat(result.getPrice()).isEqualTo(expectedPrice);
    }

    @DisplayName("해당 테마 이름이 존재하지 않으면 empty 를 반환한다.")
    @Test
    void cannotFindWithInvalidName() {
        assertThat(this.themeRepository.findByName(invalidName)).isEmpty();
    }

    @DisplayName("id로 테마를 취소한다")
    @Test
    @Order(1)
    void deleteById() {
        final boolean deleted = this.themeRepository.deleteById(themeId);

        assertThat(deleted).isTrue();
    }

    @DisplayName("id에 해당하는 테마가 없다면 false 를 반환한다")
    @Test
    @Order(2)
    void deleteWithInvalidId() {
        final boolean deleted = this.themeRepository.deleteById(invalidId);

        assertThat(deleted).isFalse();
    }

    @DisplayName("전체 테마를 불러온다.")
    @Test
    void findAll() {
        List<Theme> themes = this.themeRepository.findAll();

        assertThat(themes.size()).isEqualTo(themeId - 2);
    }
}
