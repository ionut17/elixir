package app.model.serializer;

import app.model.activity.Activity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ionut on 31-Dec-16.
 */
public class CustomActivityListSerializer extends StdSerializer<List<Activity>> {

    public CustomActivityListSerializer() {
        this(null);
    }

    public CustomActivityListSerializer(Class<List<Activity>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Activity> activities,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Activity> actv = new ArrayList<>();
        for (Activity a : activities) {
            a.setCourse(null);
            a.setType(null);
            actv.add(a);
        }

        generator.writeObject(actv);
    }
}
