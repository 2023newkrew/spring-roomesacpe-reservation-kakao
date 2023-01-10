package kakao.model.entity;

import kakao.model.response.Theme;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class Reservation {

    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

    public Reservation(Long id, LocalDate date, LocalTime time, String name, Theme theme) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.theme = theme;
    }

    public static class Column {
        public static final String ID = "id";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String NAME = "name";
        public static final String THEME_NAME = "theme_name";
        public static final String THEME_DESC = "theme_desc";
        public static final String THEME_PRICE = "theme_price";

        private Column() {}
    }
}