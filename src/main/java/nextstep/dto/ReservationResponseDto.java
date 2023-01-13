package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationResponseDto {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private String name;
    private String themeName;
    private String themeDesc;
    private Integer themePrice;

    public ReservationResponseDto() {
    }

    public ReservationResponseDto(Long id, LocalDate date, LocalTime time, String name, String themeName, String themeDesc, Integer themePrice) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeName = themeName;
        this.themeDesc = themeDesc;
        this.themePrice = themePrice;
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

    public String getThemeName() {
        return themeName;
    }

    public String getThemeDesc() {
        return themeDesc;
    }

    public Integer getThemePrice() {
        return themePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationResponseDto that = (ReservationResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(time, that.time) && Objects.equals(name, that.name) && Objects.equals(themeName, that.themeName) && Objects.equals(themeDesc, that.themeDesc) && Objects.equals(themePrice, that.themePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, name, themeName, themeDesc, themePrice);
    }

    @Override
    public String toString() {
        return "ReservationResponseDto{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", themeName='" + themeName + '\'' +
                ", themeDesc='" + themeDesc + '\'' +
                ", themePrice=" + themePrice +
                '}';
    }
}
