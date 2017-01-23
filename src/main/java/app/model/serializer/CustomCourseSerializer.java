package app.model.serializer;

import app.model.Course;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by Ionut on 31-Dec-16.
 */
public class CustomCourseSerializer extends StdSerializer<Course> {

    public CustomCourseSerializer() {
        this(null);
    }

    public CustomCourseSerializer(Class<Course> t) {
        super(t);
    }

    @Override
    public void serialize(
            Course course,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        course.setLecturers(null);
        course.setStudents(null);
        course.setActivities(null);

        generator.writeObject(course);
    }
}
