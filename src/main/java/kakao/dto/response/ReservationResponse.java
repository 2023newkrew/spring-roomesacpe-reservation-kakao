package kakao.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import kakao.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponse {

    public final Long id;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    public final LocalDate date;
    @JsonFormat(shape = Shape.STRING, pattern = "HH:mm")
    public final LocalTime time;
    public final String name;
    public final ThemeResponse theme;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
        this.name = reservation.getName();
        this.theme = new ThemeResponse(reservation.getTheme());
    }
}
