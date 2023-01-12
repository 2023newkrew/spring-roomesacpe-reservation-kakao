package nextstep.domain.dto;

public class CreateReservationDTO {
    private final String localDate;
    private final String localTime;
    private final String name;

    public CreateReservationDTO(String localDate, String localTime, String name) {
        this.localDate = localDate;
        this.localTime = localTime;
        this.name = name;
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
}
