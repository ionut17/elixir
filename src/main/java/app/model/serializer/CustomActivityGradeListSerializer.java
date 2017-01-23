package app.model.serializer;

import app.model.activity.Activity;
import app.model.activity.ActivityGrade;
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
public class CustomActivityGradeListSerializer extends StdSerializer<List<ActivityGrade>> {

    public CustomActivityGradeListSerializer() {
        this(null);
    }

    public CustomActivityGradeListSerializer(Class<List<ActivityGrade>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<ActivityGrade> grades,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {


        for (ActivityGrade g : grades) {
            g.getStudent().setGrades(null);
            g.getStudent().setAttendances(null);
            g.getStudent().setCourses(null);
            g.getStudent().setGroups(null);
        }


        generator.writeObject(grades);
    }
}
