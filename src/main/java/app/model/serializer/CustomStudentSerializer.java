package app.model.serializer;

import app.model.user.Student;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by Ionut on 31-Dec-16.
 */
public class CustomStudentSerializer extends StdSerializer<Student> {

    public CustomStudentSerializer() {
        this(null);
    }

    public CustomStudentSerializer(Class<Student> t) {
        super(t);
    }

    @Override
    public void serialize(
            Student student,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        student.setGroups(null);
        student.setCourses(null);
        student.setAttendances(null);

        generator.writeObject(student);
    }
}
