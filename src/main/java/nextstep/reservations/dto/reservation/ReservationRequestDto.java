package nextstep.reservations.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    LocalTime time;

    String name;

    String themeName;

    String themeDesc;

    Integer themePrice;

    public ReservationRequestDto() {}

    public ReservationRequestDto(final LocalDate date, final LocalTime time, final String name, final String themeName, final String themeDesc, final Integer themePrice) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeName = themeName;
        this.themeDesc = themeDesc;
        this.themePrice = themePrice;
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

    public static ReservationRequestDtoBuilder builder() {
        return new ReservationRequestDtoBuilder();}

    public static class ReservationRequestDtoBuilder {
        LocalDate date;

        LocalTime time;

        String name;

        String themeName;

        String themeDesc;

        Integer themePrice;

        ReservationRequestDtoBuilder() {
        }

        public ReservationRequestDtoBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public ReservationRequestDtoBuilder time(LocalTime time) {
            this.time = time;
            return this;
        }

        public ReservationRequestDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ReservationRequestDtoBuilder themeName(String themeName) {
            this.themeName = themeName;
            return this;
        }

        public ReservationRequestDtoBuilder themeDesc(String themeDesc) {
            this.themeDesc = themeDesc;
            return this;
        }

        public ReservationRequestDtoBuilder themePrice(Integer themePrice) {
            this.themePrice = themePrice;
            return this;
        }

        public ReservationRequestDto build() {
            return new ReservationRequestDto(date, time, name, themeName, themeDesc, themePrice);
        }
    }
}
