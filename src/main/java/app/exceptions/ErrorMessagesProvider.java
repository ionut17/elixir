package app.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ionut on 09-Feb-17.
 */
@Component("errorMessagesHandler")
public class ErrorMessagesProvider {

    public static final String INVALID_CREDENTIALS = "Datele de autentificare introduse sunt invalide.";

}
