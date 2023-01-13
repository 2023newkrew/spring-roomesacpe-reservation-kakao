package web.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import web.entity.Reservation;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationRequestDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    @Length(min = 1, max = 20)
    @NotEmpty
    @NotBlank
    private String name;
    @NotNull
    private Long themeId;

    public Reservation toEntity() {
        return Reservation.builder()
                .date(date)
                .time(time)
                .name(name)
                .themeId(themeId)
                .build();
    }
}
