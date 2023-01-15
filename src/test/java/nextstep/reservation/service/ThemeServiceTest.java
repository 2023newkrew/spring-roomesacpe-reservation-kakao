package nextstep.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import nextstep.reservation.dto.request.ThemeRequestDto;
import nextstep.reservation.dto.response.ThemeResponseDto;
import nextstep.reservation.exceptions.exception.DuplicateThemeNameException;
import nextstep.reservation.exceptions.exception.NotFoundObjectException;
import nextstep.reservation.repository.theme.ThemeMemoryRepository;
import org.junit.jupiter.api.Test;

public class ThemeServiceTest {
    private final ThemeService service;

    public ThemeServiceTest() {
        service = new ThemeService(new ThemeMemoryRepository());
    }

    @Test
    void 테마를_추가할_수_있다() {
        assertDoesNotThrow(() -> service.addTheme(new ThemeRequestDto()));
    }

    @Test
    void 테마를_가져올_수_있다() {
        Long id = service.addTheme(new ThemeRequestDto());
        assertThat(service.getTheme(id)).isInstanceOf(ThemeResponseDto.class);
    }

    @Test
    void 없는_테마를_가져올_시_예외_발생한다() {
        assertThatThrownBy(() -> service.getTheme(1L))
                .isInstanceOf(NotFoundObjectException.class);
    }

    @Test
    void 테마를_삭제할_수_있다() {
        Long id = service.addTheme(new ThemeRequestDto());
        assertDoesNotThrow(() -> service.deleteTheme(id));
        assertThatThrownBy(() -> service.getTheme(id)).isInstanceOf(NotFoundObjectException.class);
    }

    @Test
    void 없는_테마를_삭제할_시_예외가_발생한다() {
        assertThatThrownBy(() -> service.deleteTheme(1L))
                .isInstanceOf(NotFoundObjectException.class);
    }

    @Test
    void 모든_테마를_가져올_수_있다() {
        service.addTheme(new ThemeRequestDto());
        service.addTheme(new ThemeRequestDto());
        assertThat(service.getAllTheme().size()).isEqualTo(2);
    }

    @Test
    void 중복된_이름의_테마는_등록할_수_없다() {
        service.addTheme(ThemeRequestDto.builder().name("name").build());
        ThemeRequestDto sameNameThemeRequestDto = ThemeRequestDto.builder().name("name").build();
        assertThatThrownBy(() -> service.addTheme(sameNameThemeRequestDto))
                .isInstanceOf(DuplicateThemeNameException.class);
    }

}