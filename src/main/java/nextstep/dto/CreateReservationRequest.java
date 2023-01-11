package nextstep.dto;

import javax.validation.constraints.NotEmpty;

public class CreateReservationRequest {

    @NotEmpty(message = "예약 생성 시에는 날짜, 시간, 이름을 모두 기입해야 합니다.")
    private String date;
    @NotEmpty(message = "예약 생성 시에는 날짜, 시간, 이름을 모두 기입해야 합니다.")
    private String time;
    @NotEmpty(message = "예약 생성 시에는 날짜, 시간, 이름을 모두 기입해야 합니다.")
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
