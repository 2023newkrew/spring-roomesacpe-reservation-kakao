package nextstep.main.java.nextstep.mvc.domain.theme.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ThemeCreateOrUpdateRequest {
    @NotEmpty(message = "테마의 이름을 입력해주세요.")
    private String name;
    @Size(min = 10, message = "테마에 대한 설명을 10자 이상 입력해주세요.")
    private String desc;
    @Range(min = 1000L, message = "테마의 가격은 1000원 이상이여야 합니다.")
    private int price;

    public static ThemeCreateOrUpdateRequest of(String name, String desc, int price) {
        return new ThemeCreateOrUpdateRequest(name, desc, price);
    }
}
