package app.controller.common;

import app.config.DbSnapshotHolder;
import app.exceptions.ErrorMessagesProvider;
import app.model.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(path = "/api")
public class BaseController {

    @Autowired
    protected ErrorMessagesProvider emp;

}
