package roomservice.domain.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.*;

/**
 * Reservation class is Entity class, containing reservation information such as id, datetime, name and theme.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Reservation {
    private Long id; // DB에서 기본 키, auto_increment 되어있음
    private LocalDate date; // 예약에서 date, time, theme이 모두 중복되는 경우 exception(중복 예약)
    private LocalTime time;
    private String name;
    private Theme theme; // 어떤 방 탈출인가를 정의하는 객체

    public void setId(long id){
        this.id = id;
    }
}
