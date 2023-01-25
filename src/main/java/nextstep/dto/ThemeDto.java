package nextstep.dto;

import nextstep.domain.Theme;
import nextstep.exception.InvalidRequestException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class ThemeDto {

    private final String name;
    private final String desc;
    private final Integer price;

    public ThemeDto(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public ThemeDto(String params) {
        this.name = params.split(",")[0];
        this.desc = params.split(",")[1];
        this.price = Integer.parseInt(params.split(",")[2]);
    }

    public Theme toTheme() {
        validateInput(name, desc, price);
        return new Theme(name, desc, price);
    }

    private void validateInput(String name, String desc, Integer price) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(desc) || ObjectUtils.isEmpty(price)) {
            throw new InvalidRequestException();
        }
    }
}
