package kakao.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateThemeRequest {
    public long id;
    public String name;
    public String desc;
    public Integer price;
}

