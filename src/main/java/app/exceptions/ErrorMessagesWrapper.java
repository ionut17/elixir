package app.exceptions;

import java.util.*;

/**
 * Created by Ionut on 09-Feb-17.
 */
public class ErrorMessagesWrapper {

    private List<String> errorList;

    public ErrorMessagesWrapper(String... errors){
        this.errorList = new ArrayList<> (Arrays.asList(errors));
    }

    public Map<String, List> getErrors(){
        Map<String, List> map = new HashMap<>();
        map.put("errors", errorList);
        return map;
    }

}
