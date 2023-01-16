package nextstep.reservation;

import nextstep.reservation.dto.ReservationDto;
import nextstep.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class ThemeReservationMock {
    private static String[] names = {"omin", "ethan", "java"};
    private static String[] dates = {"2022-12-11", "2022-12-10", "2022-12-09"};
    private static String[] times = {"12:11", "09:11", "08:11"};

    public static Reservation makeRandomReservation(Long themeId){
        return makeRandomReservationDto(themeId).toEntity();
    }

    public static ReservationDto makeRandomReservationDto(){
        return makeRandomReservationDto(null);
    }

    public static ReservationDto makeRandomReservationDto(String date, String time){
        int index = getRandomIndex();
        return makeRandomReservationDto(date, time, names[index], null);
    }

    public static ReservationDto makeRandomReservationDto(Long themeId){
        int index = getRandomIndex();
        return makeRandomReservationDto(dates[index], times[index], names[index], themeId);
    }

    public static ReservationDto makeRandomReservationDto(String date, String time, String name, Long themeId){
        return new ReservationDto(LocalDate.parse(date), LocalTime.parse(time), name, themeId);
    }

    private static int getRandomIndex() {
        return ThreadLocalRandom.current().nextInt(names.length);
    }
}
