package nextstep.reservation.dto;

import lombok.*;
import nextstep.reservation.entity.Reservation;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class ReservationDto {
    @NonNull
    private LocalDate date;

    @NonNull
    private LocalTime time;

    @NotBlank
    private String name;

    @Setter
    private Long themeId;

    public Reservation toEntity(){
        return new Reservation(this.date, this.time, this.name, this.themeId);
    }
}
