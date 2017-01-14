package app.model.serializer;

import app.model.activity.Activity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by Ionut on 31-Dec-16.
 */
public class CustomActivitySerializer extends StdSerializer<Activity> {

    public CustomActivitySerializer() {
        this(null);
    }

    public CustomActivitySerializer(Class<Activity> t) {
        super(t);
    }

    @Override
    public void serialize(
            Activity activity,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {
        activity.setCourse(null);

        generator.writeObject(activity);
    }
}
