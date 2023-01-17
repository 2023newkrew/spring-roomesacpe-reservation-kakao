package nextstep.reservation.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.NameTransformer;
import nextstep.reservation.dto.ThemeResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ThemeResponseSerializer extends JsonSerializer<ThemeResponse> {

    private final JsonSerializer<ThemeResponse> delegate = new UnWrappedThemeResponseSerializer(NameTransformer.NOP);

    @Override
    public void serialize(ThemeResponse themeResponse, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        delegate.serialize(themeResponse, gen, serializers);
    }

    @Override
    public JsonSerializer<ThemeResponse> unwrappingSerializer(NameTransformer unwrapper) {
        return new UnWrappedThemeResponseSerializer(unwrapper);
    }
}
