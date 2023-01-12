package kakao.dto.request;

import lombok.Builder;

import java.util.Objects;

@Builder
public class UpdateThemeRequest {
    private long id;
    private String name;
    private String desc;
    private Integer price;

    public String getUpdateSQL() {
        StringBuilder builder = new StringBuilder();

        builder.append("update theme set ");
        if (!Objects.isNull(name)) builder.append("name='").append(name).append("',");
        if (!Objects.isNull(desc)) builder.append("desc='").append(desc).append("',");
        if (!Objects.isNull(price)) builder.append("price='").append(price).append("',");
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" where id=").append(id);

        return builder.toString();
    }
}
