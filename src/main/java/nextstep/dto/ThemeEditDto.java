package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeEditDto {
    private Long id;

    private String name;

    @JsonProperty("desc")
    private String description;

    private Integer price;


}
