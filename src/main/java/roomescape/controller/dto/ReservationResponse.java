package roomescape.controller.dto;

import roomescape.domain.Reservation;

public class ReservationResponse {
    private final Long id;
    private final String date;
    private final String time;
    private final String name;
    private final ThemeResponse theme;

    private ReservationResponse(Long id, String date, String time, String name, ThemeResponse theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
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

    public ThemeResponse getTheme() {
        return theme;
    }

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getDate().toString(),
                reservation.getTime().toString(),
                reservation.getName(),
                ThemeResponse.of(reservation.getTheme())
        );
    }
}
