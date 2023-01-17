package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.entity.Theme;

@Getter
@AllArgsConstructor
public abstract class ThemeBaseDto {

    private String name;

    @JsonProperty("desc")
    private String description;

    private Integer price;

    public Theme toEntity(){
        return Theme.builder()
                .name(this.name)
                .price(this.price)
                .description(this.description)
                .build();
    }

}
