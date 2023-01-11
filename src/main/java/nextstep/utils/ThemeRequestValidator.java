package nextstep.utils;

import nextstep.dto.request.CreateThemeRequest;
import nextstep.error.ApplicationException;
import nextstep.error.ErrorType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ThemeRequestValidator {

    public void validateCreateRequest(CreateThemeRequest createThemeRequest) {
        if (StringUtils.isEmpty(createThemeRequest.getName())
                || StringUtils.isEmpty(createThemeRequest.getDesc())
                || NumberUtils.isNullOrLessThanZero(createThemeRequest.getPrice())) {
            throw new ApplicationException(ErrorType.INVALID_RESERVATION_REQUEST_DATA);
        }
    }

}
