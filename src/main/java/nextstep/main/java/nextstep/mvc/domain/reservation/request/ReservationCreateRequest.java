package nextstep.main.java.nextstep.mvc.domain.reservation.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationCreateRequest {
    @FutureOrPresent(message = "지난 날짜에 예약할 수 없습니다.")
    private final LocalDate date;
    private final LocalTime time;
    @Size(min = 2, message = "이름은 두 글자 이상이여야 합니다.")
    private final String name;
    @NotEmpty(message = "예약할 테마명을 입력해 주세요.")
    private final String themeName;

    public static ReservationCreateRequest of(LocalDate date, LocalTime time, String name, String themeName) {
        return new ReservationCreateRequest(date, time, name, themeName);
    }
}
