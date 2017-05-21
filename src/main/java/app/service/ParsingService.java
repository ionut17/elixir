package app.service;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.time.ZoneOffset.UTC;

@Component("parsingService")
public class ParsingService {

    public Map<String, String> parseFirstLastName(String fullName){
        Map<String, String> parsed = new HashMap<>();
        String[] splited = fullName.trim().split(" ");
        parsed.put("lastName", WordUtils.capitalizeFully(splited[0]));
        parsed.put("firstName", WordUtils.capitalizeFully(splited[splited.length-1], '-'));
        return parsed;
    }

}
