package nextstep.domain.dto;

public class ReservationRequest {
    private final String localDate;
    private final String localTime;
    private final String name;
    private final long themeId;

    public ReservationRequest(String localDate, String localTime, String name, long themeId) {
        this.localDate = localDate;
        this.localTime = localTime;
        this.name = name;
        this.themeId = themeId;
    }

    public String getLocalDate() {
        return localDate;
    }

    public String getLocalTime() {
        return localTime;
    }

    public String getName() {
        return name;
    }

    public long getThemeId() {
        return themeId;
    }
}
