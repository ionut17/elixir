package app.model.serializer;

import app.model.activity.ActivityFile;
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
public class CustomActivityFileListSerializer extends StdSerializer<List<ActivityFile>> {

    public CustomActivityFileListSerializer() {
        this(null);
    }

    public CustomActivityFileListSerializer(Class<List<ActivityFile>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<ActivityFile> files,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {


        for (ActivityFile f : files) {
            f.getStudent().setGrades(null);
            f.getStudent().setAttendances(null);
            f.getStudent().setFiles(null);
            f.getStudent().setCourses(null);
            f.getStudent().setGroups(null);
        }


        generator.writeObject(files);
    }
}
