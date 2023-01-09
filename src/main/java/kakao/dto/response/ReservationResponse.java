package kakao.dto.response;

import kakao.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponse {
    public final Long id;
    public final LocalDate date;
    public final LocalTime time;
    public final String name;
    public final String themeName;
    public final String themeDesc;
    public final Integer themePrice;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
        this.name = reservation.getName();
        this.themeName = reservation.getTheme().getName();
        this.themeDesc = reservation.getTheme().getDesc();
        this.themePrice = reservation.getTheme().getPrice();
    }
}
