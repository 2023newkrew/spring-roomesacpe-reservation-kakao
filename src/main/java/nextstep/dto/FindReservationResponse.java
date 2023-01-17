package nextstep.dto;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.util.Objects;

public class FindReservationResponse {

    private Long reservationId;
    private String reservationDate;
    private String reservationTime;
    private String reservationName;
    private Long themeId;
    private String themeName;
    private String themeDesc;
    private Integer themePrice;

    public FindReservationResponse(
            Long reservationId, String reservationDate, String reservationTime, String reservationName,
            Long themeId, String themeName, String themeDesc, Integer themePrice) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationName = reservationName;
        this.themeId = themeId;
        this.themeName = themeName;
        this.themeDesc = themeDesc;
        this.themePrice = themePrice;
    }

    public static FindReservationResponse from(Reservation reservation, Theme theme) {
        return new FindReservationResponse(
                reservation.getId(),
                reservation.getDate().toString(),
                reservation.getTime().toString(),
                reservation.getName(),
                theme.getId(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        );
    }

    public Long getReservationId() {
        return reservationId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public String getReservationName() {
        return reservationName;
    }

    public Long getThemeId() {
        return themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public String getThemeDesc() {
        return themeDesc;
    }

    public Integer getThemePrice() {
        return themePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FindReservationResponse that = (FindReservationResponse) o;
        return reservationId.equals(that.reservationId) && reservationDate.equals(that.reservationDate) && reservationTime.equals(that.reservationTime) && reservationName.equals(that.reservationName) && themeId.equals(that.themeId) && themeName.equals(that.themeName) && themeDesc.equals(that.themeDesc) && themePrice.equals(that.themePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, reservationDate, reservationTime, reservationName, themeId, themeName, themeDesc, themePrice);
    }
}
