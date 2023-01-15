package kakao.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class Reservation {

    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Theme theme;

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