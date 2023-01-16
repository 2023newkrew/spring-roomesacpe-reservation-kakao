package kakao.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateThemeRequest {
    public long id;
    public String name;
    public String desc;
    public Integer price;

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

