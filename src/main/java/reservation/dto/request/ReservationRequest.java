package reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final LocalTime time;

    @NonNull
    private final String name;

    @NonNull
    @JsonProperty("theme_id")
    private final long themeId;

    public ReservationRequest(LocalDate date, LocalTime time, String name, long themeId) {
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

    public long getThemeId() {
        return themeId;
    }
}
