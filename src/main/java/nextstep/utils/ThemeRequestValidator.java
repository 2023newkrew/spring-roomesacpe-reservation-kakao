package nextstep.utils;

import nextstep.dto.request.CreateOrUpdateThemeRequest;
import nextstep.error.ApplicationException;
import org.apache.commons.lang3.StringUtils;

import static nextstep.error.ErrorType.INVALID_REQUEST_PARAMETER;
import static nextstep.error.ErrorType.INVALID_THEME_REQUEST_DATA;

public class ThemeRequestValidator {

    private static final int SIZE_LIMIT = 20;

    public void validateCreateOrUpdateRequest(CreateOrUpdateThemeRequest createOrUpdateThemeRequest) {
        if (StringUtils.isEmpty(createOrUpdateThemeRequest.getName())
                || StringUtils.isEmpty(createOrUpdateThemeRequest.getDesc())
                || NumberUtils.isNullOrLessThanZero(createOrUpdateThemeRequest.getPrice())) {
            throw new ApplicationException(INVALID_THEME_REQUEST_DATA, createOrUpdateThemeRequest.toString());
        }
    }

    public void validatePageableRequestParameter(String page, String size) {
        if (!StringUtils.isNumeric(page)
                || !StringUtils.isNumeric(size)
                || Integer.parseInt(size) > SIZE_LIMIT) {
            throw new ApplicationException(INVALID_REQUEST_PARAMETER, "{page: " + page + ", size: " + size + "}");
        }
    }

}
