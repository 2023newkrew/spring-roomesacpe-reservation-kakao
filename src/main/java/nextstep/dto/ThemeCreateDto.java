package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ThemeCreateDto {

    private String name;

    @JsonProperty("desc")
    private String description;

    private Integer price;
}
