package nextstep.dto;

import nextstep.domain.Reservation;

public class FindReservationResponse {

    private Long id;
    private String date;
    private String time;
    private String name;
    private Long themeId;

    private FindReservationResponse(Long id, String date, String time, String name, Long themeId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public static FindReservationResponse from(Reservation reservation) {
        return new FindReservationResponse(
                reservation.getId(),
                reservation.getDate().toString(),
                reservation.getTime().toString(),
                reservation.getName(),
                reservation.getThemeId()
        );
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
