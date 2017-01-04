package app.model.serializer;

import app.model.Course;
import app.model.user.Lecturer;
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
public class CustomCourseSerializer extends StdSerializer<List<Course>> {

    public CustomCourseSerializer() {
        this(null);
    }

    public CustomCourseSerializer(Class<List<Course>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Course> courses,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Course> cors = new ArrayList<>();
        for (Course c : courses) {
            c.setLecturers(null);
            cors.add(c);
        }

        generator.writeObject(cors);
    }
}
