package reservation.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final LocalTime time;

    @NonNull
    private final String name;

    public ReservationDto(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
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
}
