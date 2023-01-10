package nextstep.dto;

public class ReservationRequestDto {
    private String date;
    private String time;
    private String name;

    public ReservationRequestDto(String date, String time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
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
}
