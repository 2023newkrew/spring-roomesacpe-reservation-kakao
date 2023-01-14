package roomescape.console;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class InputParsing {
    Reservation createReserveInfo(long reservationIndex, String userInput){
        String params = userInput.split(" ")[1];
        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];
        String themeId = params.split(",")[3];
        Reservation reservationInfo = new Reservation(
                reservationIndex,
                LocalDate.parse(date),
                LocalTime.parse(time + ":00"),
                name,
                Long.parseLong(themeId)
        );
        return reservationInfo;
    }

    long getFindId(String userInput){
        String params = userInput.split(" ")[1];
        Long findId = Long.parseLong(params.split(",")[0]);
        return findId;
    }
}
