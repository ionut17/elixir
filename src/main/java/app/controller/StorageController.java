package app.controller;

import app.controller.common.BaseController;
import app.exceptions.ErrorMessagesWrapper;
import app.exceptions.JwtAuthenticationException;
import app.exceptions.StorageFileNotFoundException;
import app.model.activity.ActivityFile;
import app.model.dto.ResourceDto;
import app.model.user.User;
import app.service.JwtService;
import app.service.activity.ActivityFileService;
import app.service.storage.StorageService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

@RestController
public class StorageController extends BaseController {

    @Autowired
    private StorageService storageService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ActivityFileService activityFileService;

    @RequestMapping(value = "/storage/retrieve/{file_id}", params = {"k"}, method = RequestMethod.GET)
    public @ResponseBody ResponseEntity retrieveFile(@PathVariable("file_id") long fileId, @RequestParam("k") String key, UriComponentsBuilder ucBuilder, HttpServletRequest request, HttpServletResponse response) throws IOException{
        User user;
        try{
            user = jwtService.verifyToken(key);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessagesWrapper(emp.NOT_AUTHORIZED), HttpStatus.BAD_REQUEST);
        }
        if (user==null){
            return new ResponseEntity<>(new ErrorMessagesWrapper(emp.NOT_AUTHORIZED), HttpStatus.BAD_REQUEST);
        }

        ServletContext context = request.getServletContext();
        ActivityFile activityFile = activityFileService.findByFileId(fileId);
        //TODO let lecturer to access student's files who are enrolled in his courses
        if (!user.getEmail().equals(activityFile.getStudent().getEmail()) && !user.getType().equals("admin")){
            return new ResponseEntity<>(new ErrorMessagesWrapper(emp.NOT_AUTHORIZED), HttpStatus.BAD_REQUEST);
        }

        ResourceDto resourceDto = new ResourceDto(activityFile);

        if (!resourceDto.isValid()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Path filePath = storageService.load(resourceDto.toRelativePath());
        if (filePath == null){
            return new ResponseEntity<>(new ErrorMessagesWrapper(emp.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        File file = filePath.toFile();

        FileInputStream inputStream = new FileInputStream(file);
        // Get MIME type
        String mimeTypeValue = context.getMimeType(filePath.toString());
        if (mimeTypeValue == null) {
            mimeTypeValue = "application/octet-stream";
        }
        String contentDispositionValue = String.format("attachment; filename=\"%s\"", file.getName());

        byte[] output = org.apache.commons.io.IOUtils.toByteArray(inputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", contentDispositionValue);
        headers.setContentType(MediaType.parseMediaType(mimeTypeValue));
        headers.setContentLength(file.length());
        return new ResponseEntity<>(output, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/storage/upload", method = RequestMethod.POST)
    public ResponseEntity<Resource> uploadFile() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}