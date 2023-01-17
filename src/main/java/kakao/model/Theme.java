package kakao.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Theme {
    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public static class Column {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESC = "desc";
        public static final String PRICE = "price";

        private Column() {}
    }
}
