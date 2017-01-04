package app.model.serializer;

import app.model.Group;
import app.model.user.Student;
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
public class CustomGroupSerializer extends StdSerializer<List<Group>> {

    public CustomGroupSerializer() {
        this(null);
    }

    public CustomGroupSerializer(Class<List<Group>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Group> groups,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Group> grps = new ArrayList<>();
        for (Group g : groups) {
            g.setStudents(null);
            grps.add(g);
        }
        generator.writeObject(grps);
    }
}
