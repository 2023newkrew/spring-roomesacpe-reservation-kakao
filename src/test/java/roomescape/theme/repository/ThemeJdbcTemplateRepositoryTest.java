package roomescape.theme.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void findById() {
        final Theme result = this.themeRepository.findById(themeId)
                .orElseThrow(RuntimeException::new);

        assertThat(result.getId()).isEqualTo(themeId);
        assertThat(result.getName()).isEqualTo(expectedName);
        assertThat(result.getDesc()).isEqualTo(expectedDesc);
        assertThat(result.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    void cannotFindWithInvalidId() {
        assertThat(this.themeRepository.findById(invalidId)).isEmpty();
    }

    @Test
    void findByName() {
        final Theme result = this.themeRepository.findByName(expectedName)
                .orElseThrow(RuntimeException::new);

        assertThat(result.getId()).isEqualTo(themeId);
        assertThat(result.getName()).isEqualTo(expectedName);
        assertThat(result.getDesc()).isEqualTo(expectedDesc);
        assertThat(result.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    void cannotFindWithInvalidName() {
        assertThat(this.themeRepository.findByName(invalidName)).isEmpty();
    }

    @Test
    @Order(1)
    void deleteById() {
        final boolean deleted = this.themeRepository.deleteById(themeId);

        assertThat(deleted).isTrue();
    }

    @Test
    @Order(2)
    void deleteWithInvalidId() {
        final boolean deleted = this.themeRepository.deleteById(invalidId);

        assertThat(deleted).isFalse();
    }

    @Test
    void findAll() {
        List<Theme> themes = this.themeRepository.findAll();

        assertThat(themes.size()).isEqualTo(themeId - 2);
    }
}
