package nextstep.reservation.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nextstep.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class ReservationResponseDto {
    private long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private ThemeResponseDto theme;

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
        this.name = reservation.getName();
        this.theme = new ThemeResponseDto(reservation.getTheme());
    }

}
