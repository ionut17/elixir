package app.model.serializer;

import app.model.user.Lecturer;
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
public class CustomLecturerSerializer extends StdSerializer<List<Lecturer>> {

    public CustomLecturerSerializer() {
        this(null);
    }

    public CustomLecturerSerializer(Class<List<Lecturer>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Lecturer> lecturers,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Lecturer> lects = new ArrayList<>();
        for (Lecturer l : lecturers) {
            l.setCourses(null);
            lects.add(l);
        }

        generator.writeObject(lects);
    }
}
