package nextstep.reservations.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public ReservationResponseDto() {}

    public ReservationResponseDto(final Long id, final LocalDate date, final LocalTime time, final String name, final String themeName, final String themeDesc, final Integer themePrice) {
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

    public static ReservationResponseDtoBuilder builder() {return new ReservationResponseDtoBuilder();}

    public static class ReservationResponseDtoBuilder {
        private Long id;

        private LocalDate date;

        private LocalTime time;

        private String name;

        private String themeName;

        private String themeDesc;

        private Integer themePrice;

        ReservationResponseDtoBuilder() {
        }

        public ReservationResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ReservationResponseDtoBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public ReservationResponseDtoBuilder time(LocalTime time) {
            this.time = time;
            return this;
        }

        public ReservationResponseDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ReservationResponseDtoBuilder themeName(String themeName) {
            this.themeName = themeName;
            return this;
        }

        public ReservationResponseDtoBuilder themeDesc(String themeDesc) {
            this.themeDesc = themeDesc;
            return this;
        }

        public ReservationResponseDtoBuilder themePrice(Integer themePrice) {
            this.themePrice = themePrice;
            return this;
        }

        public ReservationResponseDto build() {
            return new ReservationResponseDto(id, date, time, name, themeName, themeDesc, themePrice);
        }
    }
}
