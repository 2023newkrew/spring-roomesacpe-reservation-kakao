package nextstep.entity;

import nextstep.dto.ReservationDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public Reservation(LocalDate date, LocalTime time, String name, Long themeId) {
        this(0L, date, time, name, themeId);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public static Reservation from(ReservationDto reservationDto) {
        LocalDate date = LocalDate.parse(reservationDto.getDate(), DateTimeFormatter.ISO_DATE);
        LocalTime time = LocalTime.parse(reservationDto.getTime(), DateTimeFormatter.ISO_LOCAL_TIME);
        return new Reservation(date, time, reservationDto.getName(), reservationDto.getThemeId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public Long getThemeId() {
        return themeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", theme=" + themeId +
                '}';
    }
}
