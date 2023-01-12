package nextstep.dto;

import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationResponseDto {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;

    public ReservationResponseDto() {
    }

    public ReservationResponseDto(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Theme getTheme() {
        return theme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationResponseDto that = (ReservationResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(time, that.time) && Objects.equals(name, that.name) && Objects.equals(theme, that.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, name, theme);
    }

    @Override
    public String toString() {
        return "ReservationResponseDto{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", theme=" + theme +
                '}';
    }
}
