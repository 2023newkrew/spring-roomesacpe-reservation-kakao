package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long themeId;

    public Reservation(){

    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Long themeId) {
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
        return "Id: " + id + "Date: " + date + ",Time: " + time + " ,Name: " + name + " ,ThemeId: " + themeId;
    }
}
