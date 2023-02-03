package nextstep.dto;

import lombok.Getter;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationResponseDTO {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public ReservationResponseDTO(Reservation reservation, Theme theme) {
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
        this.name = reservation.getName();
        this.theme = theme;
    }
}
