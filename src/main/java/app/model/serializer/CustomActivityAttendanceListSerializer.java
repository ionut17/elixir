package app.model.serializer;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityGrade;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ionut on 31-Dec-16.
 */
public class CustomActivityAttendanceListSerializer extends StdSerializer<List<ActivityAttendance>> {

    public CustomActivityAttendanceListSerializer() {
        this(null);
    }

    public CustomActivityAttendanceListSerializer(Class<List<ActivityAttendance>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<ActivityAttendance> attendances,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        for (ActivityAttendance a : attendances) {
            a.getStudent().setGrades(null);
            a.getStudent().setAttendances(null);
            a.getStudent().setFiles(null);
            a.getStudent().setCourses(null);
            a.getStudent().setGroups(null);
//            a.getStudent().setGrades(null);
        }

        generator.writeObject(attendances);
    }
}
