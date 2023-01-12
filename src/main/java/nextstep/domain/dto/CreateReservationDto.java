package nextstep.domain.dto;

import nextstep.domain.theme.Theme;

public class CreateReservationDto {

    private final String localDate;
    private final String localTime;
    private final String name;

    private final Theme theme;

    public CreateReservationDto(String localDate, String localTime, String name, Theme theme) {
        this.localDate = localDate;
        this.localTime = localTime;
        this.name = name;
        this.theme = theme;
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
