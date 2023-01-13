package nextstep.domain.theme;

import nextstep.domain.dto.theme.CreateThemeDto;
import nextstep.domain.dto.theme.UpdateThemeDto;
import nextstep.exception.NegativeThemePriceException;

import java.util.Objects;

public class Theme {

    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public Theme(Long id, String name, String desc, Integer price) {
        if(price < 0) {
            throw new NegativeThemePriceException();
        }
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getPrice() {
        return price;
    }

    public static Theme createTheme(CreateThemeDto createThemeDto) {
        return new Theme(
                null,
                createThemeDto.getName(),
                createThemeDto.getDesc(),
                createThemeDto.getPrice()
        );
    }

    public static Theme createTheme(UpdateThemeDto updateThemeDto) {
        return new Theme(
                updateThemeDto.getId(),
                updateThemeDto.getName(),
                updateThemeDto.getDesc(),
                updateThemeDto.getPrice()
        );
    }

    @Override
    public boolean equals(Object obj) {
        Theme theme = (Theme) obj;
        return this.id.equals(theme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
