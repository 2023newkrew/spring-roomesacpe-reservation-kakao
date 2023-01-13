package nextstep.web.common.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Response<T> {
    private final int status;
    private final String message;
    private final T data;
}
