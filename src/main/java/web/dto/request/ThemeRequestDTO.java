package web.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.domain.Theme;

@Getter
@RequiredArgsConstructor
public class ThemeRequestDTO {

    private final String name;

    private final String desc;

    private final Integer price;

    public Theme toEntity() {
        return new Theme(name, desc, price);
    }

}
