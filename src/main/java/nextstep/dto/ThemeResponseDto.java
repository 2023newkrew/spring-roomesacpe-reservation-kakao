package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeResponseDto {

    private final Long id;

    private final String name;

    @JsonProperty("desc")
    private final String description;

    private final Integer price;
}
