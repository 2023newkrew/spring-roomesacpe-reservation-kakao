package roomescape.model;

import java.time.LocalDateTime;


public class Reservation {
    private Long id;
    private LocalDateTime dateTime;
    private String name;
    private Long themeId;

    public Reservation(Long id, LocalDateTime dateTime, String name, Long themeId) {
        this.id = id;
        this.dateTime = dateTime;
        this.name = name;
        this.themeId = themeId;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getName() {
        return name;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
