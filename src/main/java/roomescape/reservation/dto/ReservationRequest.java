package roomescape.reservation.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    private LocalDate date;
    private LocalTime time;
    @NotBlank(message = "이름 필드가 비어있습니다.")
    private String name;
    @NotNull(message = "테마id 필드가 비어있습니다.")
    @Min(value = 1, message = "테마id는 1부터 시작합니다.")
    private long themeId;

    public ReservationRequest(LocalDate date, LocalTime time, String name, long themeId){
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Long getThemeId() {
        return themeId;
    }
}