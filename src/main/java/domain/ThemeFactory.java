package domain;

import kakao.dto.request.CreateThemeRequest;

public class ThemeFactory {

    public Theme create(CreateThemeRequest request) {
        return new Theme(request.name, request.desc, request.price);
    }
}
