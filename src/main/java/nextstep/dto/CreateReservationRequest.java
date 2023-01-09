package nextstep.dto;

public class CreateReservationRequest {

    private String date;
    private String time;
    private String name;

    public CreateReservationRequest(String date, String time, String name) {
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
