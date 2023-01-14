package nextstep.domain;


import nextstep.exceptions.exception.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

class ReservationTest {
    private Theme theme;

    @BeforeEach
    void setUp() {
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    }

    @DisplayName("TimeTable 에 있는 유효한 시간이면 Reservation 이 잘 생성된다.")
    @Test
    void constructNormally() {
        assertThatCode(() -> {
            new Reservation(
                    LocalDate.parse("2023-02-20"),
                    LocalTime.parse("11:30"),
                    "jay",
                    theme
            );
        }).doesNotThrowAnyException();
    }

    @DisplayName("TimeTable 에 없는 시간이면 예외가 발생한다.")
    @Test
    void constructInvalidTime() {
        assertThatThrownBy(() -> {
            new Reservation(
                    LocalDate.parse("2023-02-20"),
                    LocalTime.parse("11:00"),
                    "jay",
                    theme
            );
        }).isInstanceOf(InvalidRequestException.class);
    }

    @DisplayName("과거 시간이면 예외가 발생한다.")
    @Test
    void constructPastTime() {
        assertThatThrownBy(() -> {
            new Reservation(
                    LocalDate.parse("2023-01-01"),
                    LocalTime.parse("11:00"),
                    "jay",
                    theme
            );
        }).isInstanceOf(InvalidRequestException.class);
    }

    @DisplayName("입력 값이 유효하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {
            ";14:30;jack",
            "2023-03-01;;jack",
            "2023-03-01;14:30;"
    }, delimiter = ';')
    void constructInvalidInput(LocalDate date, LocalTime time, String name) {
        assertThatThrownBy(() -> {
            new Reservation(
                    date,
                    time,
                    name,
                    theme
            );
        }).isInstanceOf(InvalidRequestException.class);
    }
}