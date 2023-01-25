package nextstep.domain.dto;

import nextstep.domain.reservation.Reservation;

public class GetReservationDTO {
    private final Long id;
    private final String date;
    private final String time;
    private final String name;
    private final Long themeId;

    public GetReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate().toString();
        this.time = reservation.getTime().toString();
        this.name = reservation.getName();
        this.themeId = reservation.getThemeId();
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Long getThemeId() {
        return themeId;
    }
}
