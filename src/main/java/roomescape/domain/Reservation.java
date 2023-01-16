package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import static roomescape.utils.Messages.*;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public Reservation(){

    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
        checkNegativeId(themeId);
        checkEmptyName(name);
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.themeId = themeId;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public LocalDate getDate(){
        return date;
    }

    public LocalTime getTime(){
        return time;
    }

    public Long getThemeId() {
        return themeId;
    }

    public String createMessage(long reserveId){
        return "Location: /reservation/" + reserveId;
    }

    public String toMessage(){
        return RESERVATION_ID.getMessage() + id + ", " +
                RESERVATION_DATE.getMessage() + date + ", " +
                RESERVATION_TIME.getMessage() + time + ", " +
                RESERVATION_NAME.getMessage() + name + ", " +
                THEME_ID.getMessage() + themeId;
    }

    private void checkNegativeId(Long themeId){
        if (themeId < 0) {
            throw new ArithmeticException("themeId가 0보다 큰 수여야 합니다");
        }
    }

    private void checkEmptyName(String name){
        if (name.length() == 0) {
            throw new NullPointerException("name에 문자가 포함되어야 합니다");
        }
    }
}
