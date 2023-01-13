package reservation.dto.request;

import org.springframework.lang.NonNull;

public class ThemeRequest {
    @NonNull
    private final String name;

    @NonNull
    private final String desc;

    @NonNull
    private final int price;

    public ThemeRequest(@NonNull String name, @NonNull String desc, @NonNull int price) {
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
    public int getPrice() {
        return price;
    }
}
