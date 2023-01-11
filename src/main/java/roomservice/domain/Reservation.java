package roomservice.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Reservation class is a DTO class, containing reservation information such as id, datetime, name and theme.
 */
@Data
@NoArgsConstructor
public class Reservation {
    private Long id; // DB에서 기본 키, auto_increment 되어있음
    private LocalDate date; // 예약에서 date, time, theme이 모두 중복되는 경우 exception(중복 예약)
    private LocalTime time;
    private String name;
    private Theme theme; // 어떤 방 탈출인가를 정의하는 객체

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }
}
