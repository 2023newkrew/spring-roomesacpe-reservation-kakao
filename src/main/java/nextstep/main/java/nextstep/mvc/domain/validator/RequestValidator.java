package nextstep.main.java.nextstep.mvc.domain.validator;

import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ReservationCreateRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReservationCreateRequest request = (ReservationCreateRequest) target;
        ValidationUtils.rejectIfEmpty(errors, "themeName", "예약할 테마명을 입력해 주세요.");

    }
}
