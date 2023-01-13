package nextstep.reservation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nextstep.reservation.entity.Theme;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeRequestDto {

    private String name;
    private String desc;
    private Integer price;

    public Theme toEntity() {
        return Theme.builder()
                .name(name)
                .desc(desc)
                .price(price)
                .build();
    }

}
