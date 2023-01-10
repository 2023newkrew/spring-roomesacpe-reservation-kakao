package kakao.model.entity;

import kakao.model.response.Theme;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class Reservation {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }
}