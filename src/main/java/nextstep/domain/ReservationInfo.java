package nextstep.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class ReservationInfo {
    private Long id;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private String name;
    private String themeName;
    private String themeDesc;
    private Integer themePrice;

    public ReservationInfo(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
        this.name = reservation.getName();
        this.themeName = reservation.getTheme().getName();
        this.themeDesc = reservation.getTheme().getDesc();
        this.themePrice = reservation.getTheme().getPrice();
    }
}
