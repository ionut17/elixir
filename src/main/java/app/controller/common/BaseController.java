package app.controller.common;

import app.exceptions.ErrorMessagesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/api")
public class BaseController {

    @Autowired
    protected ErrorMessagesProvider emp;

}
