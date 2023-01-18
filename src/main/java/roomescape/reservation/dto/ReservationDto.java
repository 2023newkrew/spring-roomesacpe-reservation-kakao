package roomescape.reservation.dto;

public class ReservationDto {

    private String date;
    private String time;
    private String name;
    private Long themeId;

    public ReservationDto() {}

    public ReservationDto(String date, String time, String name, Long themeId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
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
