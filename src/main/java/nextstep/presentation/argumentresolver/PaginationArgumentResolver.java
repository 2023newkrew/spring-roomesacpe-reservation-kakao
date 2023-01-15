package nextstep.presentation.argumentresolver;

import nextstep.dto.request.Pageable;
import nextstep.utils.ThemeRequestValidator;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class PaginationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String PAGE = "page";
    private static final String SIZE = "size";

    private final ThemeRequestValidator themeRequestValidator;

    public PaginationArgumentResolver(ThemeRequestValidator themeRequestValidator) {
        this.themeRequestValidator = themeRequestValidator;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasPaginationAnnotation = parameter.hasParameterAnnotation(Pagination.class);
        boolean hasPageableType = Pageable.class.isAssignableFrom(parameter.getParameterType());

        return hasPaginationAnnotation && hasPageableType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String page = request.getParameter(PAGE);
        String size = request.getParameter(SIZE);

        themeRequestValidator.validatePageableRequestParameter(page, size);
        return Pageable.of(Integer.parseInt(page), Integer.parseInt(size));
    }
}
