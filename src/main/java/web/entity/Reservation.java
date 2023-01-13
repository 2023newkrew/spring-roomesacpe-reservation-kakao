package web.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public boolean isDuplicate(Reservation other) {
        return this.getDate().isEqual(other.getDate()) && this.getTime().equals(other.getTime());
    }
}
