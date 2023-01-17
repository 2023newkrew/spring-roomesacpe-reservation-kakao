package nextstep.reservation.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.NameTransformer;
import nextstep.reservation.dto.ThemeResponse;

import java.io.IOException;


public class UnWrappedThemeResponseSerializer extends JsonSerializer<ThemeResponse> {
    private final NameTransformer nameTransformer;

    public UnWrappedThemeResponseSerializer(NameTransformer nameTransformer) {
        this.nameTransformer = nameTransformer;
    }

    @Override
    public void serialize(ThemeResponse themeResponse, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("themeName", themeResponse.getName());
        gen.writeStringField("themeDesc", themeResponse.getDesc());
        gen.writeNumberField("themePrice", themeResponse.getPrice());
        gen.writeEndObject();
    }

    @Override
    public boolean isUnwrappingSerializer() {
        return true;
    }
}
