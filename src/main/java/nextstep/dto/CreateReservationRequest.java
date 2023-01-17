package nextstep.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class CreateReservationRequest {

    @NotEmpty(message = "예약 생성 시에는 날짜를 기입해야 합니다.")
    private String date;
    @NotEmpty(message = "예약 생성 시에는 시간을 기입해야 합니다.")
    private String time;
    @NotEmpty(message = "예약 생성 시에는 이름을 기입해야 합니다.")
    private String name;
    @Positive(message = "예약 생성 시에는 테마번호를 기입해야 합니다.")
    private Long themeId;

    public CreateReservationRequest() {}

    public CreateReservationRequest(String date, String time, String name, Long themeId) {
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
