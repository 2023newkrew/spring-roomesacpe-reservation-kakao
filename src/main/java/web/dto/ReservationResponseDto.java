package web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import web.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationResponseDto {

    private Long id;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private String name;
    private String themeName;
    private String themeDesc;
    private Integer themePrice;

    private ReservationResponseDto(Long id, LocalDate date, LocalTime time, String name, String themeName, String themeDesc, Integer themePrice) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeName = themeName;
        this.themeDesc = themeDesc;
        this.themePrice = themePrice;
    }

    public static ReservationResponseDto of(long id, Reservation reservation) {
        return new ReservationResponseDto(id, reservation.getDate(), reservation.getTime(), reservation.getName(), "워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }
}
