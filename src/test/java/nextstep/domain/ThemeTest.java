package nextstep.domain;

import nextstep.exceptions.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class ThemeTest {
    @DisplayName("정상적인 입력값이면 Theme 가 잘 생성된다.")
    @Test
    void constructNormally() {
        assertThatCode(() -> {
            new Theme(
                    "테마이름",
                    "테마설명",
                    22000
            );
        }).doesNotThrowAnyException();
    }

    @DisplayName("입력 값이 유효하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {
            ";테마설명;22000",
            "테마이름;;22000",
            "테마이름;테마설명;"
    }, delimiter = ';')
    void constructInvalidInput(String name, String desc, Integer price) {
        assertThatThrownBy(() -> {
            new Theme(
                    name,
                    desc,
                    price
            );
        }).isInstanceOf(InvalidRequestException.class);
    }

    @DisplayName("가격을 음수로 입력하면 예외가 발생한다.")
    @Test
    void constructInvalidPrice() {
        assertThatThrownBy(() -> {
            new Theme(
                    "테마이름",
                    "테마설명",
                    -1
            );
        }).isInstanceOf(InvalidRequestException.class);
    }
}