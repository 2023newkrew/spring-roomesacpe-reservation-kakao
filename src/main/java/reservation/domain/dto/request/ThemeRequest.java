package reservation.domain.dto.request;

import org.springframework.lang.NonNull;

public class ThemeRequest {
    @NonNull
    private final String name;

    @NonNull
    private final String desc;

    @NonNull
    private final Integer price;

    public ThemeRequest(@NonNull String name, @NonNull String desc, @NonNull Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDesc() {
        return desc;
    }

    @NonNull
    public Integer getPrice() {
        return price;
    }
}
