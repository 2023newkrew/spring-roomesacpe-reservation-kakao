package nextstep.main.java.nextstep.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReservationCreateRequestDto {
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;
}
