package nextstep.domain;

import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.exception.InvalidRequestException;

public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public Theme(String name, String desc, Integer price) {
        this(null, name, desc, price);
    }

    public Theme(Long id, String name, String desc, Integer price) {
        validate(name, desc, price);
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    private void validate(String name, String desc, Integer price) {
        if (name == null || name.length() == 0 || desc == null || desc.length() == 0 || price == null) {
            throw new InvalidRequestException(ErrorCode.INPUT_PARAMETER_INVALID);
        }
        if (price < 0) {
            throw new InvalidRequestException(ErrorCode.PRICE_INVALID);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getPrice() {
        return price;
    }
}
