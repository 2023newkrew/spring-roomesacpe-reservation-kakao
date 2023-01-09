package nextstep.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class ReservationRequestDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    LocalTime time;
    String name;

    public ReservationRequestDto(final LocalDate date, final LocalTime time, final String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }
}
