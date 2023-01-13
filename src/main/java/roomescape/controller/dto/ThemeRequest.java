package roomescape.controller.dto;

import roomescape.domain.Theme;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;

public class ThemeRequest {
    private final String name;
    private final String desc;
    private final int price;

    public ThemeRequest(String name, String desc, String price) {
        this.name = name;
        this.desc = desc;
        this.price = validatePrice(price);
    }

    private int validatePrice(String price) {
        try {
            return Integer.parseInt(price);
        } catch (NumberFormatException e) {
            throw new RoomEscapeException(ErrorCode.VALID_INPUT_REQUIRED);
        }
    }

    public Theme toEntity() {
        return new Theme(name, desc, price);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrice() {
        return price;
    }
}
