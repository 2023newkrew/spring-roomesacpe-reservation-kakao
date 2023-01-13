package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }
}
