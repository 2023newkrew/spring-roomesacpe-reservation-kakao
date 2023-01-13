package nextstep.util;

import com.google.gson.JsonObject;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;

public class ReservationInfoConverter {
    public static String convertReservationToJSONString(Reservation reservation) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", reservation.getId());
        jsonObject.addProperty("date", reservation.getDate().toString());
        jsonObject.addProperty("time", reservation.getTime().toString());
        jsonObject.addProperty("name", reservation.getName());
        Theme theme = reservation.getTheme();
        jsonObject.addProperty("themeName", theme.getName());
        jsonObject.addProperty("themeDesc", theme.getDesc());
        jsonObject.addProperty("themePrice", theme.getPrice());

        return jsonObject.toString();
    }
}
