package kakao.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Theme {
    private final String name;
    private final String desc;
    private final Integer price;
}
