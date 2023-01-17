package web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import web.entity.Reservation;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

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
    @Positive
    @JsonProperty("theme_id")
    private long themeId;
    
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

    public Reservation toEntity() {
        return Reservation.of(date, time, name, themeId);
    }

}
