package kakao.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation implements Comparable<Reservation> {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Theme theme;

    public Reservation(LocalDate date, LocalTime time, String name, Theme theme) {
        this(null, date, time, name, theme);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
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

    public String getThemeName() {
        return theme.getName();
    }

    public String getThemeDesc() {
        return theme.getDesc();
    }

    public int getThemePrice() {
        return theme.getPrice();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Reservation reservation) {
        return Long.compare(this.id, reservation.id);
    }

    public boolean isDuplicate(LocalDate date, LocalTime time) {
        if (this.date.equals(date) && this.time.equals(time)) {
            return true;
        }
        return false;
    }
}
