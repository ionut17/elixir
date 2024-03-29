package app.model.serializer;

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
public class CustomStudentListSerializer extends StdSerializer<List<Student>> {

    public CustomStudentListSerializer() {
        this(null);
    }

    public CustomStudentListSerializer(Class<List<Student>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Student> students,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Student> studs = new ArrayList<>();
        for (Student s : students) {
            s.setGroups(null);
            s.setCourses(null);
            s.setAttendances(null);
            studs.add(s);
        }

        generator.writeObject(studs);
    }
}
