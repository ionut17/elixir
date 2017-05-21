package app.service;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("timeConverter")
public class TimeConverter {

    public Date addMinutes(Date originalDate, int minutesOffset){
        Calendar cal = Calendar.getInstance();
        cal.setTime(originalDate);
        cal.add(Calendar.MINUTE, minutesOffset);
        return cal.getTime();
    }

}
